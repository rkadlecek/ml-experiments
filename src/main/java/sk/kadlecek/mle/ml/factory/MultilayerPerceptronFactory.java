package sk.kadlecek.mle.ml.factory;

import sk.kadlecek.mle.ml.bean.ClassifierWithProperties;
import weka.classifiers.functions.MultilayerPerceptron;

import java.util.*;

public class MultilayerPerceptronFactory extends AbstractAlgorithmFactory {

    String[] hiddenLayersValues = new String[0];

    Double[] learningRateValues = new Double[0];
    Double[] momentumValues = new Double[0];

    Integer[] trainingTimeEpochValues = new Integer[0];

    boolean[] autoBuildValues = new boolean[0];
    boolean[] decayValues = new boolean[0];
    boolean[] nominalToBinaryFilterValues = new boolean[0];
    boolean[] normalizeAttributesValues = new boolean[0];
    boolean[] normalizeNumericClassValues = new boolean[0];
    boolean[] resetValues = new boolean[0];

    @Override
    public ClassifierWithProperties[] generateAllClassifiers() {

        int[][] valueArrayIndexesArray = generateArrayIndexCartesianProduct(
                hiddenLayersValues.length, learningRateValues.length, momentumValues.length,
                trainingTimeEpochValues.length, autoBuildValues.length, decayValues.length,
                nominalToBinaryFilterValues.length, normalizeAttributesValues.length, normalizeNumericClassValues.length,
                resetValues.length
        );

        List<ClassifierWithProperties> classifiers = new ArrayList<>();

        for (int i = 0; i < valueArrayIndexesArray.length; i++) {
            int[] valueArrayIndexes = valueArrayIndexesArray[i];

            MultilayerPerceptron classifier = new MultilayerPerceptron();
            Map<String, String> properties = new LinkedHashMap<>();

            if (valueArrayIndexes[0] > -1) {
                String value = hiddenLayersValues[valueArrayIndexes[0]];
                properties.put("HiddenLayers", value);
                classifier.setHiddenLayers(value);
            }

            if (valueArrayIndexes[1] > -1) {
                Double value = learningRateValues[valueArrayIndexes[1]];
                properties.put("LearningRate", value.toString());
                classifier.setLearningRate(value);
            }

            if (valueArrayIndexes[2] > -1) {
                Double value = momentumValues[valueArrayIndexes[2]];
                properties.put("Momentum", value.toString());
                classifier.setMomentum(value);
            }

            if (valueArrayIndexes[3] > -1) {
                Integer value = trainingTimeEpochValues[valueArrayIndexes[3]];
                properties.put("TrainingTime", value.toString());
                classifier.setTrainingTime(value);
            }

            if (valueArrayIndexes[4] > -1) {
                Boolean value = autoBuildValues[valueArrayIndexes[4]];
                properties.put("AutoBuild", value.toString());
                classifier.setAutoBuild(value);
            }

            if (valueArrayIndexes[5] > -1) {
                Boolean value = decayValues[valueArrayIndexes[5]];
                properties.put("Decay", value.toString());
                classifier.setDecay(value);
            }

            if (valueArrayIndexes[6] > -1) {
                Boolean value = nominalToBinaryFilterValues[valueArrayIndexes[6]];
                properties.put("NominalToBinaryFilter", value.toString());
                classifier.setNominalToBinaryFilter(value);
            }

            if (valueArrayIndexes[7] > -1) {
                Boolean value = normalizeAttributesValues[valueArrayIndexes[7]];
                properties.put("NormalizeAttributes", value.toString());
                classifier.setNormalizeAttributes(value);
            }

            if (valueArrayIndexes[8] > -1) {
                Boolean value = normalizeNumericClassValues[valueArrayIndexes[8]];
                properties.put("NormalizeNumericClass", value.toString());
                classifier.setNormalizeNumericClass(value);
            }

            if (valueArrayIndexes[9] > -1) {
                Boolean value = resetValues[valueArrayIndexes[9]];
                properties.put("Reset", value.toString());
                classifier.setReset(value);
            }

            classifiers.add(new ClassifierWithProperties(classifier, properties));
        }

        return classifiers.toArray(new ClassifierWithProperties[classifiers.size()]);
    }

    @Override
    public ClassifierWithProperties generateDefaultClassifier() {
        return new ClassifierWithProperties(new MultilayerPerceptron(), new HashMap<>());
    }

    public void setHiddenLayersValues(String[] hiddenLayersValues) {
        this.hiddenLayersValues = hiddenLayersValues;
    }

    public void setLearningRateValues(Double[] learningRateValues) {
        this.learningRateValues = learningRateValues;
    }

    public void setMomentumValues(Double[] momentumValues) {
        this.momentumValues = momentumValues;
    }

    public void setTrainingTimeEpochValues(Integer[] trainingTimeEpochValues) {
        this.trainingTimeEpochValues = trainingTimeEpochValues;
    }

    public void setAutoBuildValues(boolean[] autoBuildValues) {
        this.autoBuildValues = autoBuildValues;
    }

    public void setDecayValues(boolean[] decayValues) {
        this.decayValues = decayValues;
    }

    public void setNominalToBinaryFilterValues(boolean[] nominalToBinaryFilterValues) {
        this.nominalToBinaryFilterValues = nominalToBinaryFilterValues;
    }

    public void setNormalizeAttributesValues(boolean[] normalizeAttributesValues) {
        this.normalizeAttributesValues = normalizeAttributesValues;
    }

    public void setNormalizeNumericClassValues(boolean[] normalizeNumericClassValues) {
        this.normalizeNumericClassValues = normalizeNumericClassValues;
    }

    public void setResetValues(boolean[] resetValues) {
        this.resetValues = resetValues;
    }
}
