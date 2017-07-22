package sk.kadlecek.mle.ml.factory;

import sk.kadlecek.mle.ml.bean.ClassifierWithProperties;
import weka.classifiers.rules.PART;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PARTFactory extends AbstractAlgorithmFactory {

    Float[] confidenceFactorValues = new Float[0];
    Integer[] minNumObjValues = new Integer[0];

    boolean[] binarySplitsValues = new boolean[0];
    boolean[] doNotMakeSplitPointActualValueValues = new boolean[0];
    boolean[] reducedErrorPruningValues = new boolean[0];
    boolean[] unprunedValues = new boolean[0];
    boolean[] useMDLcorrectionValues = new boolean[0];


    @Override
    public ClassifierWithProperties[] generateAllClassifiers() {

        int[][] valueArrayIndexesArray = generateArrayIndexCartesianProduct(
                confidenceFactorValues.length, minNumObjValues.length,
                binarySplitsValues.length, doNotMakeSplitPointActualValueValues.length, reducedErrorPruningValues.length,
                unprunedValues.length, useMDLcorrectionValues.length
        );

        List<ClassifierWithProperties> classifiers = new ArrayList<>();

        for (int i = 0; i < valueArrayIndexesArray.length; i++) {
            int[] valueArrayIndexes = valueArrayIndexesArray[i];

            PART classifier = new PART();
            Map<String, String> properties = new LinkedHashMap<>();

            if (valueArrayIndexes[0] > -1) {
                Float value = confidenceFactorValues[valueArrayIndexes[0]];
                properties.put("ConfidenceFactor", value.toString());
                classifier.setConfidenceFactor(value);
            }

            if (valueArrayIndexes[1] > -1) {
                Integer value = minNumObjValues[valueArrayIndexes[1]];
                properties.put("MinNumObjPerLeaf", value.toString());
                classifier.setMinNumObj(value);
            }

            if (valueArrayIndexes[2] > -1) {
                Boolean value = binarySplitsValues[valueArrayIndexes[2]];
                properties.put("BinarySplits", value.toString());
                classifier.setBinarySplits(value);
            }

            if (valueArrayIndexes[3] > -1) {
                Boolean value = doNotMakeSplitPointActualValueValues[valueArrayIndexes[3]];
                properties.put("DoNotMakeSplitPointActualValue", value.toString());
                classifier.setDoNotMakeSplitPointActualValue(value);
            }

            if (valueArrayIndexes[4] > -1) {
                Boolean value = reducedErrorPruningValues[valueArrayIndexes[4]];
                properties.put("ReducedErrorPruning", value.toString());
                classifier.setReducedErrorPruning(value);
            }

            if (valueArrayIndexes[5] > -1) {
                Boolean value = unprunedValues[valueArrayIndexes[5]];
                properties.put("Unpruned", value.toString());
                classifier.setUnpruned(value);
            }

            if (valueArrayIndexes[6] > -1) {
                Boolean value = useMDLcorrectionValues[valueArrayIndexes[6]];
                properties.put("MDLCorrection", value.toString());
                classifier.setUseMDLcorrection(value);
            }

            classifiers.add(new ClassifierWithProperties(classifier, properties));
        }

        return classifiers.toArray(new ClassifierWithProperties[classifiers.size()]);
    }

    public void setConfidenceFactorValues(Float[] confidenceFactorValues) {
        this.confidenceFactorValues = confidenceFactorValues;
    }

    public void setMinNumObjValues(Integer[] minNumObjValues) {
        this.minNumObjValues = minNumObjValues;
    }

    public void setBinarySplitsValues(boolean[] binarySplitsValues) {
        this.binarySplitsValues = binarySplitsValues;
    }

    public void setDoNotMakeSplitPointActualValueValues(boolean[] doNotMakeSplitPointActualValueValues) {
        this.doNotMakeSplitPointActualValueValues = doNotMakeSplitPointActualValueValues;
    }

    public void setReducedErrorPruningValues(boolean[] reducedErrorPruningValues) {
        this.reducedErrorPruningValues = reducedErrorPruningValues;
    }

    public void setUnprunedValues(boolean[] unprunedValues) {
        this.unprunedValues = unprunedValues;
    }

    public void setUseMDLcorrectionValues(boolean[] useMDLcorrectionValues) {
        this.useMDLcorrectionValues = useMDLcorrectionValues;
    }
}
