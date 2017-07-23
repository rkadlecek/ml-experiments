package sk.kadlecek.mle;

import sk.kadlecek.mle.ml.AbstractEvaluation;
import sk.kadlecek.mle.ml.bean.ClassifierWithProperties;
import sk.kadlecek.mle.ml.factory.J48Factory;
import sk.kadlecek.mle.ml.factory.MultilayerPerceptronFactory;
import weka.core.Instances;

import static sk.kadlecek.mle.ml.Common.*;

public class EvaluationMultilayerPerceptron extends AbstractEvaluation {

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            printUsageInfo();
            System.exit(1);
        }

        Instances trainingData = readDataFile(args[0]);
        Instances testingData = readDataFile(args[1]);

        // build classifiers
        MultilayerPerceptronFactory factory = new MultilayerPerceptronFactory();
        boolean[] booleanRange = generateBooleanRange();

        boolean[] autoBuildValues = new boolean[0];
        boolean[] decayValues = new boolean[0];
        boolean[] nominalToBinaryFilterValues = new boolean[0];
        boolean[] normalizeAttributesValues = new boolean[0];
        boolean[] normalizeNumericClassValues = new boolean[0];
        boolean[] resetValues = new boolean[0];

        String[] hiddenLayersValues = {"a", "t", "i", "o", "a,t"};
        factory.setHiddenLayersValues(hiddenLayersValues);
        factory.setLearningRateValues(generateDoubleRange(0.1,0.5, 0.1));
        factory.setMomentumValues(generateDoubleRange(0.1,0.5, 0.1));

        factory.setTrainingTimeEpochValues(generateIntegerRange(300, 700, 100));

        factory.setAutoBuildValues(booleanRange);
        factory.setDecayValues(booleanRange);
        factory.setNominalToBinaryFilterValues(booleanRange);
        factory.setNormalizeAttributesValues(booleanRange);
        factory.setNormalizeNumericClassValues(booleanRange);
        factory.setResetValues(booleanRange);

        // evaluate
        ClassifierWithProperties[] models = factory.generateAllClassifiers();
        evaluateClassifiers(models, trainingData, testingData);
    }
}
