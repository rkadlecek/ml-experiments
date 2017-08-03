package sk.kadlecek.mle;

import java.util.ArrayList;

import sk.kadlecek.mle.ml.bean.AlgorithmRunResult;
import sk.kadlecek.mle.ml.bean.AlgorithmStats;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.SMO;
import weka.classifiers.rules.DecisionTable;
import weka.classifiers.rules.PART;
import weka.classifiers.trees.DecisionStump;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;

import static sk.kadlecek.mle.ml.Common.*;

public class SingleDataset {

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            printUsageInfo();
            System.exit(1);
        }

        Instances data = readDataFile(args[0]);

        // Do 10-split cross validation
        Instances[][] split = crossValidationSplit(data, 10);

        // Separate split into training and testing arrays
        Instances[] trainingSplits = split[0];
        Instances[] testingSplits = split[1];

        // Use a set of classifiers
        Classifier[] models = {
                new J48(), // a decision tree
//                new J48() {{ setUnpruned(false); }},
                new DecisionStump(), //one-level decision tree
                new PART(),
                new DecisionTable(),//decision table majority classifier
                new NaiveBayes(),
                new RandomForest(),
                new SMO(),
                new MultilayerPerceptron()
        };

        // Run for each model
        for (int j = 0; j < models.length; j++) {

            // Collect every group of predictions for current model in a FastVector
            ArrayList<AlgorithmRunResult> algorithmRuns = new ArrayList<>();

            // For each training-testing split pair, train and test the classifier
            for (int i = 0; i < trainingSplits.length; i++) {
                Long startTime = System.currentTimeMillis();

                Evaluation validation = classify(models[j], trainingSplits[i], testingSplits[i]);

                Long endTime = System.currentTimeMillis();

                AlgorithmRunResult algorithmRunResult =
                        new AlgorithmRunResult(models[j].getClass().getSimpleName(), validation, endTime - startTime);
                algorithmRuns.add(algorithmRunResult);
            }

            // Calculate overall accuracy of current classifier on all splits
            AlgorithmStats algorithmStats = calculateStats(algorithmRuns);

            System.out.println(algorithmStats.toString());
        }
    }

    private static void printUsageInfo() {
        System.out.print("Required 1 parameter: \n");
        System.out.print("Param1: dataSet_file_path \n");
    }
}