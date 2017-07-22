package sk.kadlecek.mle;

import sk.kadlecek.mle.ml.AbstractEvaluation;
import sk.kadlecek.mle.ml.bean.ClassifierWithProperties;
import sk.kadlecek.mle.ml.factory.NaiveBayesFactory;
import weka.core.Instances;

import static sk.kadlecek.mle.ml.Common.*;


public class EvaluationNaiveBayes extends AbstractEvaluation {

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            printUsageInfo();
            System.exit(1);
        }

        Instances trainingData = readDataFile(args[0]);
        Instances testingData = readDataFile(args[1]);

        // build classifiers
        NaiveBayesFactory factory = new NaiveBayesFactory();
        boolean[] booleanRange = generateBooleanRange();

        factory.setUseKernelEstimatorValues(booleanRange);
        factory.setUseSupervisedDiscretizationValues(booleanRange);

        // evaluate
        ClassifierWithProperties[] models = factory.generateAllClassifiers();
        evaluateClassifiers(models, trainingData, testingData);
    }

}
