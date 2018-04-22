package sk.kadlecek.mle.ml.builder;

import sk.kadlecek.mle.ml.factory.AbstractAlgorithmFactory;
import sk.kadlecek.mle.ml.factory.J48Factory;
import sk.kadlecek.mle.ml.bean.ClassifierWithProperties;

import static sk.kadlecek.mle.ml.Common.*;

public class J48ConfigurationBuilder extends BaseClassifierConfigurationBuilder {

    @Override
    public ClassifierWithProperties[] buildClassifiers() {
        J48Factory factory = (J48Factory) getFactory();
        boolean[] booleanRange = generateBooleanRange();

        factory.setConfidenceFactorValues(generateFloatRange(0.1f, 0.4f, 0.1f));
        factory.setMinNumObjPerLeafValues(generateIntegerRange(0, 10, 1));
        factory.setSubtreeRaisingValues(booleanRange);
        factory.setUseLaplaceValues(booleanRange);
        factory.setUseMDLCorrectionValues(booleanRange);
        factory.setUnprunedValues(booleanRange);

        return factory.generateAllClassifiers();
    }

    @Override
    public ClassifierWithProperties bestConfiguration() {

        J48Factory factory = (J48Factory) getFactory();

        Integer[] minNumObjPerLeafValues = { 1 };
        boolean[] unprunedValues = { true };
        boolean[] mldCorrectionValues = { false };

        factory.setMinNumObjPerLeafValues(minNumObjPerLeafValues);
        factory.setUseMDLCorrectionValues(mldCorrectionValues);
        factory.setUnprunedValues(unprunedValues);

        return factory.generateAllClassifiers()[0];
    }

    @Override
    public AbstractAlgorithmFactory getFactory() {
        return new J48Factory();
    }
}
