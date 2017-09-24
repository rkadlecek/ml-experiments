package sk.kadlecek.mle.ml.builder;

import sk.kadlecek.mle.ml.bean.ClassifierWithProperties;
import sk.kadlecek.mle.ml.factory.NaiveBayesFactory;

import static sk.kadlecek.mle.ml.Common.generateBooleanRange;


public class NaiveBayesConfigurationBuilder implements ClassifierConfigurationBuilder {

    @Override
    public ClassifierWithProperties[] buildClassifiers() {
        // build classifiers
        NaiveBayesFactory factory = new NaiveBayesFactory();
        boolean[] booleanRange = generateBooleanRange();

        //factory.setUseKernelEstimatorValues(booleanRange);
        factory.setUseSupervisedDiscretizationValues(booleanRange);

        // evaluate
        return factory.generateAllClassifiers();
    }

    @Override
    public ClassifierWithProperties bestConfiguration() {

        NaiveBayesFactory factory = new NaiveBayesFactory();

        boolean[] useSupervisedDiscretizationValues = { true };

        factory.setUseSupervisedDiscretizationValues(useSupervisedDiscretizationValues);

        return factory.generateAllClassifiers()[0];
    }
}
