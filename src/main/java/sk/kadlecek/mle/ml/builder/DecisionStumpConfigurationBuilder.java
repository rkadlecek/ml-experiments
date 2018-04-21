package sk.kadlecek.mle.ml.builder;

import sk.kadlecek.mle.ml.bean.ClassifierWithProperties;
import sk.kadlecek.mle.ml.factory.AbstractAlgorithmFactory;
import sk.kadlecek.mle.ml.factory.DecisionStumpFactory;

public class DecisionStumpConfigurationBuilder extends BaseClassifierConfigurationBuilder {

    @Override
    public ClassifierWithProperties[] buildClassifiers() {
        // build classifiers
        return getFactory().generateAllClassifiers();
    }

    @Override
    public ClassifierWithProperties bestConfiguration() {
        return buildClassifiers()[0];
    }

    @Override
    public AbstractAlgorithmFactory getFactory() {
        return new DecisionStumpFactory();
    }


}
