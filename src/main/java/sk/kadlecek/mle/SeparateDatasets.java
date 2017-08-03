package sk.kadlecek.mle;

import sk.kadlecek.mle.ml.bean.AlgorithmRunResult;
import sk.kadlecek.mle.ml.bean.AlgorithmStats;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.SMO;
import weka.classifiers.rules.DecisionTable;
import weka.classifiers.rules.PART;
import weka.classifiers.trees.DecisionStump;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;

import java.util.ArrayList;

import static sk.kadlecek.mle.ml.Common.*;

public class SeparateDatasets {

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            printUsageInfo();
            System.exit(1);
        }

        String traininDataFilePath = args[0];
        String testingDataFilePath = args[1];

        Instances trainingData = readDataFile(traininDataFilePath);
        Instances testingData = readDataFile(testingDataFilePath);

        // Use a set of classifiers
        Classifier[] models = {
                new J48(), // a decision tree
                new DecisionStump(), //one-level decision tree
                new PART(), // PART decision list
                new DecisionTable(),//decision table majority classifier
                new NaiveBayes(),
                new RandomForest(),
                new SMO(), // SVM
                new MultilayerPerceptron()
        };

        // Run for each model
        for (int j = 0; j < models.length; j++) {

            // Collect every group of predictions for current model in a FastVector
            ArrayList<AlgorithmRunResult> algorithmRuns = new ArrayList<>();

            // For each training-testing split pair, train and test the classifier
            Long startTime = System.currentTimeMillis();

            Evaluation validation = classify(models[j], trainingData, testingData);

            Long endTime = System.currentTimeMillis();

            AlgorithmRunResult algorithmRunResult =
                    new AlgorithmRunResult(models[j].getClass().getSimpleName(), validation, endTime - startTime);

            algorithmRuns.add(algorithmRunResult);

            // Calculate overall accuracy of current classifier on all splits
            AlgorithmStats algorithmStats = calculateStats(algorithmRuns);

            System.out.println(algorithmStats.toString());
        }
    }

    private static void printUsageInfo() {
        System.out.print("Required 2 parameters: \n");
        System.out.print("Param1: trainingSet_file_path \n");
        System.out.print("Param2: testingSet_file_path \n");
    }
}