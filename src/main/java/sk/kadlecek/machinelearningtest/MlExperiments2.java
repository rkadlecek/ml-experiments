package sk.kadlecek.machinelearningtest;

import sk.kadlecek.machinelearningtest.ml.AlgorithmRunResult;
import sk.kadlecek.machinelearningtest.ml.AlgorithmStats;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.NominalPrediction;
import weka.classifiers.evaluation.Prediction;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.SMO;
import weka.classifiers.rules.DecisionTable;
import weka.classifiers.rules.PART;
import weka.classifiers.trees.DecisionStump;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class MlExperiments2 {

    public static void main(String[] args) throws Exception {
        BufferedReader trainingDatafile = readDataFile("preprocessor_output.txt");
        BufferedReader testDatafile = readDataFile("mobileshop_phones_dataset.arff");

        Instances trainingData = new Instances(trainingDatafile);
        trainingData.setClassIndex(trainingData.numAttributes() - 1);

        Instances testData = new Instances(testDatafile);
        testData.setClassIndex(testData.numAttributes() - 1);

        // Do 10-split cross validation
        //Instances[][] split = crossValidationSplit(data, 10);

        // Separate split into training and testing arrays
        //Instances[] trainingSplits = split[0];
        //Instances[] testingSplits = split[1];

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
            //for (int i = 0; i < trainingSplits.length; i++) {
                Long startTime = System.currentTimeMillis();

                Evaluation validation = classify(models[j], trainingData, testData);

                Long endTime = System.currentTimeMillis();

                AlgorithmRunResult algorithmRunResult =
                        new AlgorithmRunResult(models[j].getClass().getSimpleName(), validation, endTime - startTime);
                algorithmRuns.add(algorithmRunResult);

                // Uncomment to see the summary for each training-testing pair.
                //System.out.println(models[j].toString());
//                System.out.println(models[j].getClass().getSimpleName() + " Run: " + i);
//                System.out.println("Weighted F-Measure: " + String.format("%.2f%%", validation.weightedFMeasure() * 100));
//                System.out.println("Weighted Precision: " + String.format("%.2f%%", validation.weightedPrecision() * 100));
//                System.out.println("Weighted Recall: " + String.format("%.2f%%", validation.weightedRecall() * 100));
            //}



            // Calculate overall accuracy of current classifier on all splits
            AlgorithmStats algorithmStats = calculateStats(algorithmRuns);

            //double accuracy = calculateAccuracy(evaluations);
            //Long averageTime = calculateAverageTime(times);

            // Print current classifier's name and accuracy in a complicated,
            // but nice-looking way.
//            System.out.println(models[j].getClass().getSimpleName() + ": "
//                    + "Accuracy: " + String.format("%.2f%%", algorithmStats.getAvgWeightedAccuracy()) +
//                    ", Average time taken: " + String.format("%.2f miliSeconds", algorithmStats.getAvgTime())
//                    + "\n---------------------------------");
            System.out.print(algorithmStats.toString());

        }
    }

    public static BufferedReader readDataFile(String filename) {
        BufferedReader inputReader = null;

        try {
            inputReader = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException ex) {
            System.err.println("File not found: " + filename);
        }

        return inputReader;
    }

    public static Evaluation classify(Classifier model,
                                      Instances trainingSet, Instances testingSet) throws Exception {
        Evaluation evaluation = new Evaluation(trainingSet);

        model.buildClassifier(trainingSet);
        evaluation.evaluateModel(model, testingSet);

        return evaluation;
    }

    public static AlgorithmStats calculateStats(ArrayList<AlgorithmRunResult> runResults) {

        double sumWeightedAccuracy = 0;
        double sumWeightedPrecision = 0;
        double sumWeightedRecall = 0;
        double sumWeightedFMeasure = 0;
        long sumTime = 0;

        int count = runResults.size();
        String classifierName = (runResults.size() > 0) ? runResults.get(0).getClassifierName() : "N/A";
        for (int i = 0; i < runResults.size(); i++) {
            AlgorithmRunResult result = runResults.get(i);
            Evaluation evaluation = result.getEvaluation();

            sumWeightedAccuracy += calculateAccuracy(evaluation);
            sumWeightedPrecision += evaluation.weightedPrecision();
            sumWeightedRecall += evaluation.weightedRecall();
            sumWeightedFMeasure += evaluation.weightedFMeasure();
            sumTime += result.getTime();
        }

        AlgorithmStats stats = new AlgorithmStats();
        stats.setClassifierName(classifierName);
        if (runResults.size() > 0) {
            stats.setAvgWeightedAccuracy(sumWeightedAccuracy / count);
            stats.setAvgWeightedPrecision(sumWeightedPrecision / count);
            stats.setAvgWeightedRrecall(sumWeightedRecall / count);
            stats.setAvgWeightedFMeasure(sumWeightedFMeasure / count);
            stats.setAvgTime((double) sumTime / count);
        }
        return stats;
    }

    public static double calculateAccuracy(Evaluation evaluation) {
        double correct = 0;

        List<Prediction> predictions = evaluation.predictions();
        for (int i = 0; i < predictions.size(); i++) {
            NominalPrediction np = (NominalPrediction) predictions.get(i);
            if (np.predicted() == np.actual()) {
                correct++;
            }
        }

        return correct / predictions.size();
    }

    public static Instances[][] crossValidationSplit(Instances data, int numberOfFolds) {
        Instances[][] split = new Instances[2][numberOfFolds];

        for (int i = 0; i < numberOfFolds; i++) {
            split[0][i] = data.trainCV(numberOfFolds, i);
            split[1][i] = data.testCV(numberOfFolds, i);
        }

        return split;
    }

    public static Long calculateAverageTime(ArrayList<Long> times) {
        Long sumTime = 0l;
        for (Long time : times) {
            sumTime += time;
        }
        return sumTime / times.size();
    }

}