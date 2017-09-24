package sk.kadlecek.mle.ml.builder;

import sk.kadlecek.mle.ml.bean.ClassifierWithProperties;

public interface ClassifierConfigurationBuilder {

    ClassifierWithProperties[] buildClassifiers();

    ClassifierWithProperties bestConfiguration();
}
