package sk.kadlecek.mle.ml.builder;

import sk.kadlecek.mle.ml.bean.ClassifierWithProperties;
import sk.kadlecek.mle.ml.factory.MultilayerPerceptronFactory;

import static sk.kadlecek.mle.ml.Common.*;

public class MultilayerPerceptronConfigurationBuilder implements ClassifierConfigurationBuilder {

    @Override
    public ClassifierWithProperties[] buildClassifiers() {
        // build classifiers
        MultilayerPerceptronFactory factory = new MultilayerPerceptronFactory();
        boolean[] booleanRange = generateBooleanRange();

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
        return factory.generateAllClassifiers();
    }
}
