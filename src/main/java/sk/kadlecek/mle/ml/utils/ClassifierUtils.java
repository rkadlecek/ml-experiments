package sk.kadlecek.mle.ml.utils;

import sk.kadlecek.mle.ml.builder.*;

public class ClassifierUtils {

    public static ClassifierConfigurationBuilder getBuilderForAlgorithm(String algorithm) throws IllegalArgumentException {
        switch (algorithm) {
            case "J48":
                return new J48ConfigurationBuilder();
            case "DecisionStump":
                return new DecisionStumpConfigurationBuilder();
            case "DecisionTable":
                return new DecisionTableConfigurationBuilder();
            case "PART":
                return new PARTConfigurationBuilder();
            case "RandomForest":
                return new RandomForestConfigurationBuilder();
            case "NaiveBayes":
                return new NaiveBayesConfigurationBuilder();
            case "SMO":
                return new SMOConfigurationBuilder();
            case "MultilayerPerceptron":
                return new MultilayerPerceptronConfigurationBuilder();
            default:
                throw new IllegalArgumentException("Unknown algorithm: " + algorithm);
        }
    }

}
