package sk.kadlecek.mle;

import sk.kadlecek.mle.ml.AbstractEvaluation;
import sk.kadlecek.mle.ml.bean.ClassifierWithProperties;
import sk.kadlecek.mle.ml.factory.PARTFactory;
import weka.core.Instances;

import static sk.kadlecek.mle.ml.Common.*;

public class EvaluationPART extends AbstractEvaluation {

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            printUsageInfo();
            System.exit(1);
        }

        Instances trainingData = readDataFile(args[0]);
        Instances testingData = readDataFile(args[1]);

        // build classifiers
        PARTFactory factory = new PARTFactory();
        boolean[] booleanRange = generateBooleanRange();

        factory.setConfidenceFactorValues(generateFloatRange(0.1f, 0.4f, 0.1f));
        factory.setMinNumObjValues(generateIntegerRange(0,10, 1));

        factory.setUnprunedValues(booleanRange);
        factory.setBinarySplitsValues(booleanRange);
        factory.setDoNotMakeSplitPointActualValueValues(booleanRange);
        factory.setReducedErrorPruningValues(booleanRange);
        factory.setUseMDLcorrectionValues(booleanRange);


        // evaluate
        ClassifierWithProperties[] models = factory.generateAllClassifiers();
        evaluateClassifiers(models, trainingData, testingData);
    }
}
