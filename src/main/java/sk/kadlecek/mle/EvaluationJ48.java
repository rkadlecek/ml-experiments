package sk.kadlecek.mle;

import sk.kadlecek.mle.ml.AbstractEvaluation;
import sk.kadlecek.mle.ml.factory.J48Factory;
import sk.kadlecek.mle.ml.bean.ClassifierWithProperties;
import weka.core.Instances;

import static sk.kadlecek.mle.ml.Common.*;

public class EvaluationJ48 extends AbstractEvaluation {

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            printUsageInfo();
            System.exit(1);
        }

        Instances trainingData = readDataFile(args[0]);
        Instances testingData = readDataFile(args[1]);

        // build classifiers
        J48Factory factory = new J48Factory();
        boolean[] booleanRange = generateBooleanRange();

        factory.setConfidenceFactorValues(generateFloatRange(0.1f, 0.4f, 0.1f));
        factory.setNumFoldsValues(generateIntegerRange(1, 3, 1));
        factory.setSubtreeRaisingValues(booleanRange);
        factory.setUseLaplaceValues(booleanRange);
        factory.setUseMDLCorrectionValues(booleanRange);
        factory.setUnprunedValues(booleanRange);

        // evaluate
        ClassifierWithProperties[] models = factory.generateAllClassifiers();
        evaluateClassifiers(models, trainingData, testingData);
    }

}
