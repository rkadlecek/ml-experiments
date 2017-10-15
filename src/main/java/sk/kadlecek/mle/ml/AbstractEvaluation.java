package sk.kadlecek.mle.ml;

import org.apache.commons.cli.CommandLine;
import sk.kadlecek.mle.ml.bean.AlgorithmStats;
import sk.kadlecek.mle.ml.bean.ClassifierWithProperties;
import sk.kadlecek.mle.ml.builder.ClassifierConfigurationBuilder;
import sk.kadlecek.mle.ml.runnable.CrossValidationEvaluateClassifierCallable;
import sk.kadlecek.mle.ml.runnable.EvaluateClassifierCallable;
import sk.kadlecek.mle.ml.utils.ClassifierUtils;
import sk.kadlecek.mle.ml.utils.CommandLineUtils;
import weka.classifiers.Classifier;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instances;
import weka.core.stemmers.LovinsStemmer;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.util.*;
import java.util.concurrent.*;

public class AbstractEvaluation {

    protected static void printUsageInfo() {
        System.out.print("Required 2 parameters: \n");
        System.out.print("Param1: trainingSet_file_path \n");
        System.out.print("Param2: testingSet_file_path \n");
    }

    protected static ClassifierWithProperties[] buildClassifiers(CommandLine commandLine) {
        String algorithm = CommandLineUtils.getSelectedAlgorithm(commandLine);
        ClassifierConfigurationBuilder builder = ClassifierUtils.getBuilderForAlgorithm(algorithm);

        if (CommandLineUtils.hasUseOnlyBestConfigurationOption(commandLine)) {
            return new ClassifierWithProperties[]{ builder.bestConfiguration() };
        } else {
            return builder.buildClassifiers();
        }
    }

    protected static void crossValidationEvaluateClassifiers(
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

        waitUntilComplete(futures, threadPool);
    }

    protected static void evaluateClassifiers(ClassifierWithProperties[] models, Instances trainingData, Instances testingData,
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

        waitUntilComplete(futures, threadPool);
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

    private static void waitUntilComplete(Set<Future<AlgorithmStats>> futures, ExecutorService threadPool)
            throws Exception {

        Map<Integer, AlgorithmStats> statsMap = new HashMap<>();

        boolean run = true;
        while (run) {
            boolean allCompleted = true;
            Iterator<Future<AlgorithmStats>> iter = futures.iterator();

            while (iter.hasNext()) {
                Future<AlgorithmStats> f = iter.next();
                if (f.isDone()) {
                    System.out.println(f.get().toStringNoLabels());
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
    }

}
