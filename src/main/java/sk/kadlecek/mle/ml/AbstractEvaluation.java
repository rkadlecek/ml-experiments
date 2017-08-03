package sk.kadlecek.mle.ml;

import sk.kadlecek.mle.ml.bean.AlgorithmStats;
import sk.kadlecek.mle.ml.bean.ClassifierWithProperties;
import sk.kadlecek.mle.ml.runnable.EvaluateClassifierCallable;
import weka.classifiers.Classifier;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instances;
import weka.core.stemmers.LovinsStemmer;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.*;

public class AbstractEvaluation {

    protected static void printUsageInfo() {
        System.out.print("Required 2 parameters: \n");
        System.out.print("Param1: trainingSet_file_path \n");
        System.out.print("Param2: testingSet_file_path \n");
    }

    protected static void evaluateClassifiers(ClassifierWithProperties[] models, Instances trainingData, Instances testingData,
                                              boolean vectorizeStrings)
        throws Exception {

        //ExecutorService cachedPool = Executors.newFixedThreadPool(4);
        ExecutorService cachedPool = new ThreadPoolExecutor(4, 4, 60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());

        // Run for each model
        Set<Future<AlgorithmStats>> futures = new HashSet<>();

        for (int j = 0; j < models.length; j++) {

            if (vectorizeStrings) {
                FilteredClassifier fc = createFilteredStringClassifier(models[j].getClassifier(), trainingData);
                models[j].setClassifier(fc);
            }

            // submit threads
            Future<AlgorithmStats> future = cachedPool.submit(new EvaluateClassifierCallable(j, models[j], trainingData, testingData));
            models[j] = null;
            futures.add(future);
        }

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
                cachedPool.shutdown();
                run = false;
            }else {
                Thread.sleep(500);
            }
        }
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

}
