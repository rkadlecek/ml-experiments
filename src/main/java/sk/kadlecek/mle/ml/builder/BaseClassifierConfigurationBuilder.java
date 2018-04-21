package sk.kadlecek.mle.ml.builder;

import sk.kadlecek.mle.ml.bean.ClassifierWithProperties;
import sk.kadlecek.mle.ml.factory.AbstractAlgorithmFactory;

public abstract class BaseClassifierConfigurationBuilder implements ClassifierConfigurationBuilder {

    @Override
    public ClassifierWithProperties defaultConfiguration() {
        return getFactory().generateDefaultClassifier();
    }

    protected abstract AbstractAlgorithmFactory getFactory();
}
