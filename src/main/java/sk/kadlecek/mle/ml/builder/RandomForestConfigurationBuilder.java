package sk.kadlecek.mle.ml.builder;

import sk.kadlecek.mle.ml.bean.ClassifierWithProperties;
import sk.kadlecek.mle.ml.factory.RandomForestFactory;

import static sk.kadlecek.mle.ml.Common.generateBooleanRange;
import static sk.kadlecek.mle.ml.Common.generateIntegerRange;

public class RandomForestConfigurationBuilder implements ClassifierConfigurationBuilder {

    @Override
    public ClassifierWithProperties[] buildClassifiers() {
        // build classifiers
        RandomForestFactory factory = new RandomForestFactory();
        boolean[] booleanRange = generateBooleanRange();

        //factory.setBatchSizeValues(generateIntegerRange(80, 120, 10));
        factory.setMaxDepthValues(generateIntegerRange(0, 15, 1));
        factory.setNumFeaturesValues(generateIntegerRange(0, 30, 1));
        factory.setNumBaggingIterationsValues(generateIntegerRange(80, 120, 10));

        factory.setBreakTiesRandomlyValues(booleanRange);

        // evaluate
        return factory.generateAllClassifiers();
    }
}
