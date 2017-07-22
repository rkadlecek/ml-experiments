package sk.kadlecek.mle;

import sk.kadlecek.mle.ml.AbstractEvaluation;
import sk.kadlecek.mle.ml.bean.ClassifierWithProperties;
import sk.kadlecek.mle.ml.factory.RandomForestFactory;
import weka.core.Instances;

import static sk.kadlecek.mle.ml.Common.*;

public class EvaluationRandomForest extends AbstractEvaluation {

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            printUsageInfo();
            System.exit(1);
        }

        Instances trainingData = readDataFile(args[0]);
        Instances testingData = readDataFile(args[1]);

        // build classifiers
        RandomForestFactory factory = new RandomForestFactory();
        boolean[] booleanRange = generateBooleanRange();

        factory.setBatchSizeValues(generateIntegerRange(80, 120, 10));
        factory.setMaxDepthValues(generateIntegerRange(0, 15, 1));
        factory.setNumFeaturesValues(generateIntegerRange(0, 30, 1));
        factory.setNumBaggingIterationsValues(generateIntegerRange(80, 120, 10));

        factory.setBreakTiesRandomlyValues(booleanRange);

        // evaluate
        ClassifierWithProperties[] models = factory.generateAllClassifiers();
        evaluateClassifiers(models, trainingData, testingData);
    }
}
