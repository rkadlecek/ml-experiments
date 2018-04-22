package sk.kadlecek.mle.ml.builder;

import sk.kadlecek.mle.ml.bean.ClassifierWithProperties;
import sk.kadlecek.mle.ml.factory.AbstractAlgorithmFactory;
import sk.kadlecek.mle.ml.factory.PARTFactory;

import static sk.kadlecek.mle.ml.Common.*;

public class PARTConfigurationBuilder extends BaseClassifierConfigurationBuilder {

    @Override
    public ClassifierWithProperties[] buildClassifiers() {
        // build classifiers
        PARTFactory factory = (PARTFactory) getFactory();
        boolean[] booleanRange = generateBooleanRange();

        factory.setConfidenceFactorValues(generateFloatRange(0.1f, 0.4f, 0.1f));
        factory.setMinNumObjValues(generateIntegerRange(0,10, 1));

        factory.setBinarySplitsValues(booleanRange);
        factory.setDoNotMakeSplitPointActualValueValues(booleanRange);

        factory.setUseMDLcorrectionValues(booleanRange);
        factory.setUnprunedValues(booleanRange);
        factory.setReducedErrorPruningValues(booleanRange);


        // evaluate
        return factory.generateAllClassifiers();
    }

    @Override
    public ClassifierWithProperties bestConfiguration() {

        PARTFactory factory = (PARTFactory) getFactory();

        Float[] confidenceFactorValues = { 0.1f };
        Integer[] minNumObjPerLeafValues = { 1 };
        boolean[] unprunedValues = { false };
        boolean[] reducedErrorPruningValues = { false };
        boolean[] mldCorrectionValues = { true };

        factory.setConfidenceFactorValues(confidenceFactorValues);
        factory.setMinNumObjValues(minNumObjPerLeafValues);

        factory.setUnprunedValues(unprunedValues);
        factory.setReducedErrorPruningValues(reducedErrorPruningValues);
        factory.setUseMDLcorrectionValues(mldCorrectionValues);

        return factory.generateAllClassifiers()[0];
    }

    @Override
    public AbstractAlgorithmFactory getFactory() {
        return new PARTFactory();
    }
}
