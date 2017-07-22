package sk.kadlecek.mle.ml;

import sk.kadlecek.mle.ml.bean.AlgorithmRunResult;
import sk.kadlecek.mle.ml.bean.AlgorithmStats;
import sk.kadlecek.mle.ml.bean.ClassifierWithProperties;
import weka.classifiers.Evaluation;
import weka.core.Instances;

import java.util.ArrayList;

import static sk.kadlecek.mle.ml.Common.calculateStats;
import static sk.kadlecek.mle.ml.Common.classify;

public class AbstractEvaluation {

    protected static void printUsageInfo() {
        System.out.print("Required 2 parameters: \n");
        System.out.print("Param1: trainingSet_file_path \n");
        System.out.print("Param2: testingSet_file_path \n");
    }

    protected static void evaluateClassifiers(ClassifierWithProperties[] models, Instances trainingData, Instances testingData)
        throws Exception {

        // Run for each model
        for (int j = 0; j < models.length; j++) {

            // Collect every group of predictions for current model in a FastVector
            ArrayList<AlgorithmRunResult> algorithmRuns = new ArrayList<>();

            // For each training-testing split pair, train and test the classifier
            Long startTime = System.currentTimeMillis();

            Evaluation validation = classify(models[j].getClassifier(), trainingData, testingData);

            Long endTime = System.currentTimeMillis();

            AlgorithmRunResult algorithmRunResult =
                    new AlgorithmRunResult(models[j].getClassifierSimpleName(), validation, endTime - startTime);

            algorithmRuns.add(algorithmRunResult);

            // Calculate overall accuracy of current classifier on all splits
            AlgorithmStats algorithmStats = calculateStats(algorithmRuns);

            System.out.print(models[j].getProperties().toString() + "\t");
            System.out.println(algorithmStats.toStringNoLabels());
        }
    }

}
