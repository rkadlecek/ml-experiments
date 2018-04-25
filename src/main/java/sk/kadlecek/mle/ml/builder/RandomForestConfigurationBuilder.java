package sk.kadlecek.mle.ml.builder;

import sk.kadlecek.mle.ml.bean.ClassifierWithProperties;
import sk.kadlecek.mle.ml.factory.AbstractAlgorithmFactory;
import sk.kadlecek.mle.ml.factory.J48Factory;
import sk.kadlecek.mle.ml.factory.RandomForestFactory;

import static sk.kadlecek.mle.ml.Common.generateBooleanRange;
import static sk.kadlecek.mle.ml.Common.generateIntegerRange;

public class RandomForestConfigurationBuilder extends BaseClassifierConfigurationBuilder {

    @Override
    public ClassifierWithProperties[] buildClassifiers() {
        // build classifiers
        RandomForestFactory factory = (RandomForestFactory) getFactory();
        boolean[] booleanRange = generateBooleanRange();

        factory.setBatchSizeValues(generateIntegerRange(80, 120, 10));
        factory.setMaxDepthValues(generateIntegerRange(1, 16, 2));
        factory.setNumFeaturesValues(generateIntegerRange(1, 10, 1));
        factory.setNumBaggingIterationsValues(generateIntegerRange(80, 120, 10));

        factory.setBreakTiesRandomlyValues(booleanRange);

        // evaluate
        return factory.generateAllClassifiers();
    }

    @Override
    public ClassifierWithProperties bestConfiguration() {

        RandomForestFactory factory = (RandomForestFactory) getFactory();

        Integer[] batchSizeValues = { 80 };
        Integer[] maxDepthValues = { 15 };
        Integer[] numFeaturesValues = { 3 };
        Integer[] numBaggingIterationsValues = { 90 };
        boolean[] breakTiesRandomlyValues = { true };

        factory.setBatchSizeValues(batchSizeValues);
        factory.setMaxDepthValues(maxDepthValues);
        factory.setNumFeaturesValues(numFeaturesValues);
        factory.setNumBaggingIterationsValues(numBaggingIterationsValues);

        factory.setBreakTiesRandomlyValues(breakTiesRandomlyValues);

        return factory.generateAllClassifiers()[0];
    }

    @Override
    public AbstractAlgorithmFactory getFactory() {
        return new RandomForestFactory();
    }
}
