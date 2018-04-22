package sk.kadlecek.mle.ml.runnable;

import sk.kadlecek.mle.ml.bean.AlgorithmRunResult;
import sk.kadlecek.mle.ml.bean.AlgorithmStats;
import sk.kadlecek.mle.ml.bean.ClassifierWithProperties;
import weka.classifiers.Evaluation;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.Random;
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
    private int runs;

    public  CrossValidationEvaluateClassifierCallable(int id, ClassifierWithProperties modelWithProperties, Instances dataset, int folds, int runs) {
        this.id = id;
        this.modelWithProperties = modelWithProperties;
        this.dataset = dataset;
        this.folds = folds;
        this.runs = runs;
    }

    @Override
    public AlgorithmStats call() throws Exception {
        ArrayList<AlgorithmRunResult> algorithmRuns = new ArrayList<>();

        for (int i = 0; i < runs; i++) {

            Evaluation validation = new Evaluation(dataset);

            Long startTime = System.currentTimeMillis();
            // randomize the data, stratify test/training set and run #folds crossvalidation

            validation.crossValidateModel(modelWithProperties.getClassifier(), dataset, folds, new Random(i));
            Long endTime = System.currentTimeMillis();

            AlgorithmRunResult algorithmRunResult =
                    new AlgorithmRunResult(modelWithProperties.getClassifierSimpleName(), validation, endTime - startTime);

            System.err.println("Done [" + i + "/" + runs + "]: " + modelWithProperties.getProperties() + " in " + (endTime - startTime) + "ms");

            algorithmRuns.add(algorithmRunResult);

        }

        AlgorithmStats algorithmStats = calculateStats(algorithmRuns);
        algorithmStats.setClassifierId(id);

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
