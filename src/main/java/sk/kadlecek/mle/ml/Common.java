package sk.kadlecek.mle.ml;

import sk.kadlecek.mle.ml.bean.AlgorithmRunResult;
import sk.kadlecek.mle.ml.bean.AlgorithmStats;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.NominalPrediction;
import weka.classifiers.evaluation.Prediction;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Common {

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

    public static Instances readDataFile(String filename) throws IOException {
        BufferedReader inputReader = null;

        inputReader = new BufferedReader(new FileReader(filename));
        Instances dataInstances = new Instances(inputReader);
        dataInstances.setClassIndex(dataInstances.numAttributes() - 1);

        return dataInstances;
    }

    public static Evaluation classify(Classifier model,
                                      Instances trainingSet, Instances testingSet) throws Exception {
        Evaluation evaluation = new Evaluation(trainingSet);

        model.buildClassifier(trainingSet);
        evaluation.evaluateModel(model, testingSet);

        return evaluation;
    }

    public static Float[] generateFloatRange(float from, float to, float step) {
        List<Float> lst = new ArrayList<>();
        for (float i = from; i <= to; i += step) {
            lst.add(i);
        }
        return lst.toArray(new Float[0]);
    }

    public static Double[] generateDoubleRange(double from, double to, double step) {
        List<Double> lst = new ArrayList<>();
        for (double i = from; i <= to; i += step) {
            lst.add(i);
        }
        return lst.toArray(new Double[0]);
    }

    public static Integer[] generateIntegerRange(int from, int to, int step) {
        List<Integer> lst = new ArrayList<>();
        for (int i = from; i <= to; i += step) {
            lst.add(i);
        }
        return lst.toArray(new Integer[0]);
    }

    public static int[] generateIntRange(int from, int to, int step) {
        Integer[] arrInteger = generateIntegerRange(from, to, step);

        int[] arrInt = new int[arrInteger.length];

        for (int i = 0; i < arrInteger.length; i++) {
            arrInt[i] = arrInteger[i];
        }
        return arrInt;
    }

    public static boolean[] generateBooleanRange() {
        boolean[] arr = new boolean[2];
        arr[0] = false;
        arr[1] = true;
        return arr;
    }
}
