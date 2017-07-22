package sk.kadlecek.mle.ml.factory;

import sk.kadlecek.mle.ml.bean.ClassifierWithProperties;
import weka.classifiers.trees.J48;

import java.util.*;

public class J48Factory extends AbstractAlgorithmFactory {

    Float[] confidenceFactorValues = new Float[0];
    Integer[] numFoldsValues = new Integer[0];
    boolean[] subtreeRaisingValues = new boolean[0];
    boolean[] useLaplaceValues = new boolean[0];
    boolean[] useMDLCorrectionValues = new boolean[0];



    @Override
    public ClassifierWithProperties[] generateAllClassifiers() {
        int[][] valueArrayIndexesArray = generateArrayIndexCartesianProduct(
                confidenceFactorValues.length, numFoldsValues.length,
                subtreeRaisingValues.length, useLaplaceValues.length,
                useMDLCorrectionValues.length);

        List<ClassifierWithProperties> classifiers = new ArrayList<>();

        for (int i = 0; i < valueArrayIndexesArray.length; i++) {
            int[] valueArrayIndexes = valueArrayIndexesArray[i];

            J48 classifier = new J48();
            Map<String, String> properties = new TreeMap<>();


            if (valueArrayIndexes[0] > -1) {
                Float value = confidenceFactorValues[valueArrayIndexes[0]];
                properties.put("ConfidenceFactor", value.toString());
                classifier.setConfidenceFactor(value);
            }

            if (valueArrayIndexes[1] > -1) {
                Integer value = numFoldsValues[valueArrayIndexes[1]];
                properties.put("NumFolds", value.toString());
                classifier.setNumFolds(value);
            }

            if (valueArrayIndexes[2] > -1) {
                Boolean value = subtreeRaisingValues[valueArrayIndexes[2]];
                properties.put("SubTreeRaising", value.toString());
                classifier.setSubtreeRaising(value);
            }

            if (valueArrayIndexes[3] > -1) {
                Boolean value = subtreeRaisingValues[valueArrayIndexes[3]];
                properties.put("UseLaplace", value.toString());
                classifier.setUseLaplace(value);
            }

            if (valueArrayIndexes[4] > -1) {
                Boolean value = useMDLCorrectionValues[valueArrayIndexes[4]];
                properties.put("UseMDLCorrection", value.toString());
                classifier.setUseMDLcorrection(value);
            }

            classifiers.add(new ClassifierWithProperties(classifier, properties));
        }

        return classifiers.toArray(new ClassifierWithProperties[classifiers.size()]);
    }

    public void setConfidenceFactorValues(Float[] confidenceFactorValues) {
        this.confidenceFactorValues = confidenceFactorValues;
    }

    public void setNumFoldsValues(Integer[] numFoldsValues) {
        this.numFoldsValues = numFoldsValues;
    }

    public void setSubtreeRaisingValues(boolean[] subtreeRaisingValues) {
        this.subtreeRaisingValues = subtreeRaisingValues;
    }

    public void setUseLaplaceValues(boolean[] useLaplaceValues) {
        this.useLaplaceValues = useLaplaceValues;
    }

    public void setUseMDLCorrectionValues(boolean[] useMDLCorrectionValues) {
        this.useMDLCorrectionValues = useMDLCorrectionValues;
    }
}
