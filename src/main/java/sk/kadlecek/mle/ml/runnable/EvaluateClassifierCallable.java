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

public class EvaluateClassifierCallable implements Callable<AlgorithmStats>, Supplier<AlgorithmStats> {

    private int id;
    private ClassifierWithProperties modelWithProperties;
    private Instances trainingData;
    private Instances testingData;

    public EvaluateClassifierCallable(int id, ClassifierWithProperties modelWithProperties, Instances trainingData, Instances testingData) {
        this.id = id;
        this.modelWithProperties = modelWithProperties;
        this.trainingData = trainingData;
        this.testingData = testingData;
    }

    @Override
    public AlgorithmStats call() throws Exception {
        // Collect every group of predictions for current model in a FastVector
        ArrayList<AlgorithmRunResult> algorithmRuns = new ArrayList<>();

        // For each training-testing split pair, train and test the classifier
        Long startTime = System.currentTimeMillis();

        Evaluation validation = classify(modelWithProperties.getClassifier(), trainingData, testingData);

        Long endTime = System.currentTimeMillis();

        AlgorithmRunResult algorithmRunResult =
                new AlgorithmRunResult(modelWithProperties.getClassifierSimpleName(), validation, endTime - startTime);

        algorithmRuns.add(algorithmRunResult);

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
