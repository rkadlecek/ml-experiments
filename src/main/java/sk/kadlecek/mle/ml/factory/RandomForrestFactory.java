package sk.kadlecek.mle.ml.factory;

import sk.kadlecek.mle.ml.bean.ClassifierWithProperties;

public class RandomForrestFactory extends AbstractAlgorithmFactory {

    @Override
    public ClassifierWithProperties[] generateAllClassifiers() {
        return new ClassifierWithProperties[0];
    }
}
