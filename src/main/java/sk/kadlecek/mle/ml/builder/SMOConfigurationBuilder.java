package sk.kadlecek.mle.ml.builder;

import sk.kadlecek.mle.ml.bean.ClassifierWithProperties;
import sk.kadlecek.mle.ml.factory.AbstractAlgorithmFactory;
import sk.kadlecek.mle.ml.factory.SMOFactory;
import weka.classifiers.functions.supportVector.Kernel;
import weka.classifiers.functions.supportVector.PolyKernel;
import weka.classifiers.functions.supportVector.Puk;
import weka.classifiers.functions.supportVector.RBFKernel;

import static sk.kadlecek.mle.ml.Common.generateBooleanRange;
import static sk.kadlecek.mle.ml.Common.generateDoubleRange;

public class SMOConfigurationBuilder extends BaseClassifierConfigurationBuilder {

    @Override
    public ClassifierWithProperties[] buildClassifiers() {
        // build classifiers
        SMOFactory factory = (SMOFactory) getFactory();
        boolean[] booleanRange = generateBooleanRange();

        Kernel[] kernels = {
                new PolyKernel(),
                new Puk(),
                new RBFKernel()
        };

        factory.setComplexityConstantValues(generateDoubleRange(0.7, 1.5, 0.1));
        factory.setKernelValues(kernels);
        factory.setBuildCalibrationModelValues(booleanRange);

        // evaluate
        return factory.generateAllClassifiers();
    }

    @Override
    public ClassifierWithProperties bestConfiguration() {

        SMOFactory factory = (SMOFactory) getFactory();

        Double[] complexityConstantValues = { 1.4d };
        Kernel[] kernelValues = { new Puk() };
        boolean[] buildCalibrationModelValues = { true };

        factory.setComplexityConstantValues(complexityConstantValues);
        factory.setKernelValues(kernelValues);
        factory.setBuildCalibrationModelValues(buildCalibrationModelValues);

        return factory.generateAllClassifiers()[0];
    }

    @Override
    public AbstractAlgorithmFactory getFactory() {
        return new SMOFactory();
    }
}
