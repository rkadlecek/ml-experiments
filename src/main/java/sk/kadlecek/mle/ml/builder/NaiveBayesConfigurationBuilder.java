package sk.kadlecek.mle.ml.builder;

import sk.kadlecek.mle.ml.bean.ClassifierWithProperties;
import sk.kadlecek.mle.ml.factory.AbstractAlgorithmFactory;
import sk.kadlecek.mle.ml.factory.NaiveBayesFactory;

import static sk.kadlecek.mle.ml.Common.generateBooleanRange;


public class NaiveBayesConfigurationBuilder extends BaseClassifierConfigurationBuilder {

    @Override
    public ClassifierWithProperties[] buildClassifiers() {
        // build classifiers
        NaiveBayesFactory factory = (NaiveBayesFactory) getFactory();
        boolean[] booleanRange = generateBooleanRange();

        //factory.setUseKernelEstimatorValues(booleanRange);
        factory.setUseSupervisedDiscretizationValues(booleanRange);

        // evaluate
        return factory.generateAllClassifiers();
    }

    @Override
    public ClassifierWithProperties bestConfiguration() {

        NaiveBayesFactory factory = (NaiveBayesFactory) getFactory();

        boolean[] useSupervisedDiscretizationValues = { true };

        factory.setUseSupervisedDiscretizationValues(useSupervisedDiscretizationValues);

        return factory.generateAllClassifiers()[0];
    }

    @Override
    public AbstractAlgorithmFactory getFactory() {
        return new NaiveBayesFactory();
    }
}
