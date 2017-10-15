package sk.kadlecek.mle.ml;

import org.apache.commons.cli.CommandLine;
import sk.kadlecek.mle.ml.bean.AlgorithmStats;
import sk.kadlecek.mle.ml.bean.ClassifierPredictionResult;
import sk.kadlecek.mle.ml.bean.ClassifierRunnableResult;
import sk.kadlecek.mle.ml.bean.ClassifierWithProperties;
import sk.kadlecek.mle.ml.builder.ClassifierConfigurationBuilder;
import sk.kadlecek.mle.ml.runnable.CrossValidationEvaluateClassifierCallable;
import sk.kadlecek.mle.ml.runnable.EvaluateClassifierCallable;
import sk.kadlecek.mle.ml.runnable.PredictUsingClassifierRunnable;
import sk.kadlecek.mle.ml.utils.ClassifierUtils;
import sk.kadlecek.mle.ml.utils.CommandLineUtils;
import weka.classifiers.Classifier;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instances;
import weka.core.stemmers.LovinsStemmer;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

import static sk.kadlecek.mle.ml.Common.writeDataFile;

public class AbstractEvaluation {

    protected static ClassifierWithProperties[] buildClassifiers(CommandLine commandLine) {
        String algorithm = CommandLineUtils.getSelectedAlgorithm(commandLine);
        ClassifierConfigurationBuilder builder = ClassifierUtils.getBuilderForAlgorithm(algorithm);

        if (CommandLineUtils.hasUseOnlyBestConfigurationOption(commandLine)) {
            return new ClassifierWithProperties[]{ builder.bestConfiguration() };
        } else {
            return builder.buildClassifiers();
        }
    }

    protected static Map<Integer, AlgorithmStats> crossValidationEvaluateClassifiers(
            ClassifierWithProperties[] models, Instances dataset, int folds, int runs)
        throws Exception {

        ExecutorService threadPool = initThreadPool();

        // Run for each model
        Set<Future<AlgorithmStats>> futures = new HashSet<>();

        for (int j = 0; j < models.length; j++) {

            // submit threads
            Future<AlgorithmStats> future = threadPool.submit(
                    new CrossValidationEvaluateClassifierCallable(j, models[j], dataset, folds, runs)
            );
            models[j] = null;
            futures.add(future);
        }

        return waitUntilComplete(futures, threadPool);
    }

    protected static Map<Integer, AlgorithmStats> evaluateClassifiers(ClassifierWithProperties[] models, Instances trainingData, Instances testingData,
                                              boolean vectorizeStrings)
            throws Exception {

        ExecutorService threadPool = initThreadPool();

        // Run for each model
        Set<Future<AlgorithmStats>> futures = new HashSet<>();

        for (int j = 0; j < models.length; j++) {

            if (vectorizeStrings) {
                FilteredClassifier fc = createFilteredStringClassifier(models[j].getClassifier(), trainingData);
                models[j].setClassifier(fc);
            }

            // submit threads
            Future<AlgorithmStats> future = threadPool.submit(new EvaluateClassifierCallable(j, models[j], trainingData, testingData));
            models[j] = null;
            futures.add(future);
        }

        return waitUntilComplete(futures, threadPool);
    }

    protected static Map<Integer, ClassifierPredictionResult> predictUsingClassifiers(ClassifierWithProperties[] models, Instances trainingData, Instances testingData)
            throws Exception {

        ExecutorService threadPool = initThreadPool();

        // Run for each model
        Set<Future<ClassifierPredictionResult>> futures = new HashSet<>();

        for (int j = 0; j < models.length; j++) {

            // submit threads
            Future<ClassifierPredictionResult> future = threadPool.submit(
                    new PredictUsingClassifierRunnable(j, models[j], trainingData, testingData)
            );
            models[j] = null;
            futures.add(future);
        }

        return waitUntilComplete(futures, threadPool);
    }

    private static ExecutorService initThreadPool() {
        return new ThreadPoolExecutor(4, 4, 60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());
    }

    private static FilteredClassifier createFilteredStringClassifier(Classifier classifier, Instances trainingData) throws Exception {
        StringToWordVector filter = new StringToWordVector();
        filter.setInputFormat(trainingData);
        filter.setIDFTransform(true);
        filter.setTFTransform(true);

        LovinsStemmer stemmer = new LovinsStemmer();
        filter.setStemmer(stemmer);
        filter.setLowerCaseTokens(true);

        FilteredClassifier fc = new FilteredClassifier();
        fc.setClassifier(classifier);
        fc.setFilter(filter);
        return fc;
    }

    private static <T extends ClassifierRunnableResult> Map<Integer, T> waitUntilComplete(Set<Future<T>> futures, ExecutorService threadPool)
            throws Exception {

        Map<Integer, T> resultMap = new HashMap<>();

        boolean run = true;
        while (run) {
            boolean allCompleted = true;
            Iterator<Future<T>> iter = futures.iterator();

            while (iter.hasNext()) {
                Future<T> f = iter.next();
                if (f.isDone()) {
                    resultMap.put(f.get().getClassifierId(), f.get());
                    iter.remove();
                }else {
                    allCompleted = false;
                }
            }

            if (allCompleted) {
                threadPool.shutdown();
                run = false;
            }else {
                Thread.sleep(500);
            }
        }
        return resultMap;
    }

    protected static void printAlgorithmStats(Collection<AlgorithmStats> algorithmStatsCol) {
        for (AlgorithmStats algStats: algorithmStatsCol) {
            System.out.println(algStats.toStringNoLabels());
        }
    }

    protected static void savePredictionResults(Map<Integer, ClassifierPredictionResult> predictionResults, String path)
            throws IOException {

        Map<Integer, String> propertiesMap = new HashMap<>();

        String classifierName = null;
        for (Map.Entry<Integer, ClassifierPredictionResult> entry : predictionResults.entrySet()) {
            Integer key = entry.getKey();
            ClassifierPredictionResult value = entry.getValue();
            if (classifierName == null) {
                classifierName = value.getClassifierName();
            }
            propertiesMap.put(key, entry.getValue().getProperties().toString());
            writeDataFile(path + value.getClassifierName() + "_" + key + ".arff", value.getClassifiedDataset());
        }
        saveMappingFile(propertiesMap, classifierName, path);
    }

    protected static void saveMappingFile(Map<Integer, String> propertiesMap, String classifierName, String path) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path + "/" + classifierName + "_mapping.txt"));
        for (Map.Entry<Integer, String> entry : propertiesMap.entrySet()) {
            writer.write(entry.getKey() + "\t" + entry.getValue());
            writer.newLine();
        }
        writer.flush();
        writer.close();
    }
}
