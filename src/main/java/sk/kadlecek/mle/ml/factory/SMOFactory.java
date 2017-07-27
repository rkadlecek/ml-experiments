package sk.kadlecek.mle.ml.factory;

import sk.kadlecek.mle.ml.bean.ClassifierWithProperties;
import weka.classifiers.functions.SMO;
import weka.classifiers.functions.supportVector.Kernel;
import weka.classifiers.trees.RandomForest;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SMOFactory extends AbstractAlgorithmFactory {

    Double[] complexityConstantValues = new Double[0];
    //double[] toleranceParameterValues = new double[0];
    //double[] epsilonValues = new double[0];
    Kernel[] kernelValues = new Kernel[0];
    boolean[] buildCalibrationModelValues = new boolean[0];


    @Override
    public ClassifierWithProperties[] generateAllClassifiers() {
        int[][] valueArrayIndexesArray = generateArrayIndexCartesianProduct(
                complexityConstantValues.length, kernelValues.length, buildCalibrationModelValues.length
        );

        List<ClassifierWithProperties> classifiers = new ArrayList<>();

        for (int i = 0; i < valueArrayIndexesArray.length; i++) {
            int[] valueArrayIndexes = valueArrayIndexesArray[i];

            SMO classifier = new SMO();
            Map<String, String> properties = new LinkedHashMap<>();

            if (valueArrayIndexes[0] > -1) {
                Double value = complexityConstantValues[valueArrayIndexes[0]];
                properties.put("ComplexityConstant", value.toString());
                classifier.setC(value);
            }

            if (valueArrayIndexes[1] > -1) {
                Kernel value = kernelValues[valueArrayIndexes[1]];
                properties.put("Kernel", value.toString());
                classifier.setKernel(value);
            }

            if (valueArrayIndexes[2] > -1) {
                Boolean value = buildCalibrationModelValues[valueArrayIndexes[2]];
                properties.put("BuildCalibrationModel", value.toString());
                classifier.setBuildCalibrationModels(value);
            }

            classifiers.add(new ClassifierWithProperties(classifier, properties));
        }

        return classifiers.toArray(new ClassifierWithProperties[classifiers.size()]);
    }

    public void setComplexityConstantValues(Double[] complexityConstantValues) {
        this.complexityConstantValues = complexityConstantValues;
    }

    public void setKernelValues(Kernel[] kernelValues) {
        this.kernelValues = kernelValues;
    }

    public void setBuildCalibrationModelValues(boolean[] buildCalibrationModelValues) {
        this.buildCalibrationModelValues = buildCalibrationModelValues;
    }
}
