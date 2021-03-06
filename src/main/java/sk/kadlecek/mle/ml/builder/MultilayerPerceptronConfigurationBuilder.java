package sk.kadlecek.mle.ml.builder;

import sk.kadlecek.mle.ml.bean.ClassifierWithProperties;
import sk.kadlecek.mle.ml.factory.AbstractAlgorithmFactory;
import sk.kadlecek.mle.ml.factory.MultilayerPerceptronFactory;

import static sk.kadlecek.mle.ml.Common.*;

public class MultilayerPerceptronConfigurationBuilder extends BaseClassifierConfigurationBuilder {

    @Override
    public ClassifierWithProperties[] buildClassifiers() {
        // build classifiers
        MultilayerPerceptronFactory factory = (MultilayerPerceptronFactory) getFactory();
        boolean[] booleanRange = generateBooleanRange();

        String[] hiddenLayersValues = {"a", "t", "i", "o", "a,t"};
        factory.setHiddenLayersValues(hiddenLayersValues);
        factory.setLearningRateValues(generateDoubleRange(0.1,0.6, 0.2));
        factory.setMomentumValues(generateDoubleRange(0.1,0.6, 0.2));

        factory.setTrainingTimeEpochValues(generateIntegerRange(200, 800, 200));

        //factory.setAutoBuildValues(booleanRange);
        factory.setDecayValues(booleanRange);
        //factory.setNominalToBinaryFilterValues(booleanRange);
        factory.setNormalizeAttributesValues(booleanRange);
        //factory.setNormalizeNumericClassValues(booleanRange);
        //factory.setResetValues(booleanRange);

        // evaluate
        return factory.generateAllClassifiers();
    }

    @Override
    public ClassifierWithProperties bestConfiguration() {

        // build classifiers
        MultilayerPerceptronFactory factory = (MultilayerPerceptronFactory) getFactory();

        String[] hiddenLayersValues = { "a,t" };
        Double[] learningRateValues = { 0.3 };
        Double[] momentumValues = { 0.5 };
        Integer[] trainingTimeEpochsValues = { 800 };
        boolean[] decayValues = { false };
        boolean[] normalizeAttributesValues = { true };


        factory.setHiddenLayersValues(hiddenLayersValues);
        factory.setLearningRateValues(learningRateValues);
        factory.setMomentumValues(momentumValues);

        factory.setTrainingTimeEpochValues(trainingTimeEpochsValues);

        factory.setDecayValues(decayValues);
        factory.setNormalizeAttributesValues(normalizeAttributesValues);

        return factory.generateAllClassifiers()[0];
    }

    @Override
    public AbstractAlgorithmFactory getFactory() {
        return new MultilayerPerceptronFactory();
    }

}
