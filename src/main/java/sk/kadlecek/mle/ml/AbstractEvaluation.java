package sk.kadlecek.mle.ml;

import sk.kadlecek.mle.ml.bean.AlgorithmStats;
import sk.kadlecek.mle.ml.bean.ClassifierWithProperties;
import sk.kadlecek.mle.ml.runnable.EvaluateClassifierCallable;
import weka.core.Instances;

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

    protected static void evaluateClassifiers(ClassifierWithProperties[] models, Instances trainingData, Instances testingData)
        throws Exception {

        //ExecutorService cachedPool = Executors.newCachedThreadPool();
        ExecutorService cachedPool = new ThreadPoolExecutor(5, 5, 60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());


        // Run for each model
        Set<Future<AlgorithmStats>> futures = new HashSet<Future<AlgorithmStats>>();

        for (int j = 0; j < models.length; j++) {
            Future<AlgorithmStats> future = cachedPool.submit(new EvaluateClassifierCallable(j, models[j], trainingData, testingData));
            futures.add(future);
        }

        while (true) {
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
            }
            Thread.sleep(500);
        }
    }

}
