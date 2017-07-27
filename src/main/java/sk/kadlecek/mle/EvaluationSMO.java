package sk.kadlecek.mle;

import sk.kadlecek.mle.ml.AbstractEvaluation;
import sk.kadlecek.mle.ml.bean.ClassifierWithProperties;
import sk.kadlecek.mle.ml.factory.SMOFactory;
import weka.classifiers.functions.supportVector.*;
import weka.core.Instances;

import static sk.kadlecek.mle.ml.Common.*;

public class EvaluationSMO extends AbstractEvaluation {

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            printUsageInfo();
            System.exit(1);
        }

        Instances trainingData = readDataFile(args[0]);
        Instances testingData = readDataFile(args[1]);

        // build classifiers
        SMOFactory factory = new SMOFactory();
        boolean[] booleanRange = generateBooleanRange();

        Kernel[] kernels = {
                new PolyKernel(),
                new Puk(),
                new RBFKernel()
        };

        factory.setComplexityConstantValues(generateDoubleRange(0.7, 1.5, 0.1));
        factory.setKernelValues(kernels);
        factory.setBuildCalibrationModelValues(booleanRange);

        // evaluate
        ClassifierWithProperties[] models = factory.generateAllClassifiers();
        evaluateClassifiers(models, trainingData, testingData);
    }

}
