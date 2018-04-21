package sk.kadlecek.mle.ml.factory;

import sk.kadlecek.mle.ml.bean.ClassifierWithProperties;
import weka.classifiers.bayes.NaiveBayes;

import java.util.*;

public class NaiveBayesFactory extends AbstractAlgorithmFactory {

    boolean[] useKernelEstimatorValues = new boolean[0];
    boolean[] useSupervisedDiscretizationValues = new boolean[0];

    @Override
    public ClassifierWithProperties[] generateAllClassifiers() {
        int[][] valueArrayIndexesArray = generateArrayIndexCartesianProduct(
                useKernelEstimatorValues.length, useSupervisedDiscretizationValues.length
        );

        List<ClassifierWithProperties> classifiers = new ArrayList<>();

        for (int i = 0; i < valueArrayIndexesArray.length; i++) {
            int[] valueArrayIndexes = valueArrayIndexesArray[i];

            NaiveBayes classifier = new NaiveBayes();
            Map<String, String> properties = new TreeMap<>();

            if (valueArrayIndexes[0] > -1) {
                Boolean value = useKernelEstimatorValues[valueArrayIndexes[0]];
                properties.put("UseKernelEstimatorValues", value.toString());
                classifier.setUseKernelEstimator(value);
            }

            if (valueArrayIndexes[1] > -1) {
                Boolean value = useSupervisedDiscretizationValues[valueArrayIndexes[1]];
                properties.put("UseSupervisedDiscretization", value.toString());
                classifier.setUseSupervisedDiscretization(value);
            }

            classifiers.add(new ClassifierWithProperties(classifier, properties));
        }

        return classifiers.toArray(new ClassifierWithProperties[classifiers.size()]);
    }

    @Override
    public ClassifierWithProperties generateDefaultClassifier() {
        return new ClassifierWithProperties(new NaiveBayes(), new HashMap<>());
    }

    public void setUseKernelEstimatorValues(boolean[] useKernelEstimatorValues) {
        this.useKernelEstimatorValues = useKernelEstimatorValues;
    }

    public void setUseSupervisedDiscretizationValues(boolean[] useSupervisedDiscretizationValues) {
        this.useSupervisedDiscretizationValues = useSupervisedDiscretizationValues;
    }
}
