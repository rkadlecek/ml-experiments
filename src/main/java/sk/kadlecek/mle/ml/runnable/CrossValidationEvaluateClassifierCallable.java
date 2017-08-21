package sk.kadlecek.mle.ml.runnable;

import sk.kadlecek.mle.ml.bean.AlgorithmRunResult;
import sk.kadlecek.mle.ml.bean.AlgorithmStats;
import sk.kadlecek.mle.ml.bean.ClassifierWithProperties;
import weka.classifiers.Evaluation;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

import static sk.kadlecek.mle.ml.Common.calculateStats;
import static sk.kadlecek.mle.ml.Common.classify;
import static sk.kadlecek.mle.ml.Common.crossValidationSplit;

public class CrossValidationEvaluateClassifierCallable implements Callable<AlgorithmStats>, Supplier<AlgorithmStats> {

    private int id;
    private ClassifierWithProperties modelWithProperties;
    private Instances dataset;
    private int folds;

    public CrossValidationEvaluateClassifierCallable(int id, ClassifierWithProperties modelWithProperties, Instances dataset, int folds) {
        this.id = id;
        this.modelWithProperties = modelWithProperties;
        this.dataset = dataset;
        this.folds = folds;
    }

    @Override
    public AlgorithmStats call() throws Exception {
        // split dataset into <number_of_folds> training and testing split datasets
        Instances[][] splits = crossValidationSplit(dataset, folds);

        // Separate split into training and testing arrays
        Instances[] trainingSplits = splits[0];
        Instances[] testingSplits = splits[1];

        ArrayList<AlgorithmRunResult> algorithmRuns = new ArrayList<>();

        for (int i = 0; i < trainingSplits.length; i++) {
            Long startTime = System.currentTimeMillis();

            Evaluation validation = classify(modelWithProperties.getClassifier(), trainingSplits[i], testingSplits[i]);

            Long endTime = System.currentTimeMillis();

            AlgorithmRunResult algorithmRunResult =
                    new AlgorithmRunResult(modelWithProperties.getClassifierSimpleName(), validation, endTime - startTime);

            algorithmRuns.add(algorithmRunResult);
        }

        // Calculate overall accuracy of current classifier on all splits
        AlgorithmStats algorithmStats = calculateStats(algorithmRuns);

        algorithmStats.setProperties(modelWithProperties.getProperties());
        return algorithmStats;
    }

    @Override
    public AlgorithmStats get() {
        try {
            return call();
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
