package sk.kadlecek.mle.ml.builder;

import sk.kadlecek.mle.ml.bean.ClassifierWithProperties;
import sk.kadlecek.mle.ml.factory.DecisionStumpFactory;

public class DecisionStumpConfigurationBuilder implements ClassifierConfigurationBuilder {

    @Override
    public ClassifierWithProperties[] buildClassifiers() {
        // build classifiers
        DecisionStumpFactory factory = new DecisionStumpFactory();

        // evaluate
        return factory.generateAllClassifiers();
    }

    @Override
    public ClassifierWithProperties bestConfiguration() {
        return buildClassifiers()[0];
    }
}
