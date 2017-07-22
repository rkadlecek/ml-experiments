package sk.kadlecek.mle.ml.factory;

import sk.kadlecek.mle.ml.bean.ClassifierWithProperties;
import weka.classifiers.trees.RandomForest;

import java.util.*;

public class RandomForestFactory extends AbstractAlgorithmFactory {

    Integer[] batchSizeValues = new Integer[0];
    Integer[] maxDepthValues = new Integer[0];
    Integer[] numDecimalPlacesValues = new Integer[0];
    Integer[] numFeaturesValues = new Integer[0];
    Integer[] numBaggingIterationsValues = new Integer[0];

    boolean[] breakTiesRandomlyValues = new boolean[0];

    @Override
    public ClassifierWithProperties[] generateAllClassifiers() {
        int[][] valueArrayIndexesArray = generateArrayIndexCartesianProduct(
                batchSizeValues.length, maxDepthValues.length,
                numDecimalPlacesValues.length, numFeaturesValues.length, numBaggingIterationsValues.length,
                breakTiesRandomlyValues.length
        );

        List<ClassifierWithProperties> classifiers = new ArrayList<>();

        for (int i = 0; i < valueArrayIndexesArray.length; i++) {
            int[] valueArrayIndexes = valueArrayIndexesArray[i];

            RandomForest classifier = new RandomForest();
            Map<String, String> properties = new LinkedHashMap<>();

            if (valueArrayIndexes[0] > -1) {
                Integer value = batchSizeValues[valueArrayIndexes[0]];
                properties.put("BatchSize", value.toString());
                classifier.setBatchSize(value.toString());
            }

            if (valueArrayIndexes[1] > -1) {
                Integer value = maxDepthValues[valueArrayIndexes[1]];
                properties.put("MaxDepth", value.toString());
                classifier.setMaxDepth(value);
            }

            if (valueArrayIndexes[2] > -1) {
                Integer value = numDecimalPlacesValues[valueArrayIndexes[2]];
                properties.put("NumDecimalPlaces", value.toString());
                classifier.setNumDecimalPlaces(value);
            }

            if (valueArrayIndexes[3] > -1) {
                Integer value = numFeaturesValues[valueArrayIndexes[3]];
                properties.put("NumFeatures", value.toString());
                classifier.setNumFeatures(value);
            }

            if (valueArrayIndexes[4] > -1) {
                Integer value = numBaggingIterationsValues[valueArrayIndexes[4]];
                properties.put("NumBaggingIterations", value.toString());
                classifier.setNumIterations(value);
            }

            if (valueArrayIndexes[5] > -1) {
                Boolean value = breakTiesRandomlyValues[valueArrayIndexes[5]];
                properties.put("BreakTiesRandomly", value.toString());
                classifier.setBreakTiesRandomly(value);
            }

            classifiers.add(new ClassifierWithProperties(classifier, properties));
        }

        return classifiers.toArray(new ClassifierWithProperties[classifiers.size()]);

    }


    public void setBatchSizeValues(Integer[] batchSizeValues) {
        this.batchSizeValues = batchSizeValues;
    }

    public void setMaxDepthValues(Integer[] maxDepthValues) {
        this.maxDepthValues = maxDepthValues;
    }

    public void setNumDecimalPlacesValues(Integer[] numDecimalPlacesValues) {
        this.numDecimalPlacesValues = numDecimalPlacesValues;
    }

    public void setNumFeaturesValues(Integer[] numFeaturesValues) {
        this.numFeaturesValues = numFeaturesValues;
    }

    public void setBreakTiesRandomlyValues(boolean[] breakTiesRandomlyValues) {
        this.breakTiesRandomlyValues = breakTiesRandomlyValues;
    }

    public void setNumBaggingIterationsValues(Integer[] numBaggingIterationsValues) {
        this.numBaggingIterationsValues = numBaggingIterationsValues;
    }
}
