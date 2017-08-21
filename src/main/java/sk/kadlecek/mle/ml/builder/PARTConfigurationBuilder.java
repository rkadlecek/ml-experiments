package sk.kadlecek.mle.ml.builder;

import sk.kadlecek.mle.ml.bean.ClassifierWithProperties;
import sk.kadlecek.mle.ml.factory.PARTFactory;

import static sk.kadlecek.mle.ml.Common.*;

public class PARTConfigurationBuilder implements ClassifierConfigurationBuilder {

    @Override
    public ClassifierWithProperties[] buildClassifiers() {
        // build classifiers
        PARTFactory factory = new PARTFactory();
        boolean[] booleanRange = generateBooleanRange();

        factory.setConfidenceFactorValues(generateFloatRange(0.1f, 0.4f, 0.1f));
        factory.setMinNumObjValues(generateIntegerRange(0,10, 1));

        factory.setUnprunedValues(booleanRange);
        //factory.setBinarySplitsValues(booleanRange);
        //factory.setDoNotMakeSplitPointActualValueValues(booleanRange);
        factory.setReducedErrorPruningValues(booleanRange);
        factory.setUseMDLcorrectionValues(booleanRange);


        // evaluate
        return factory.generateAllClassifiers();
    }
}
