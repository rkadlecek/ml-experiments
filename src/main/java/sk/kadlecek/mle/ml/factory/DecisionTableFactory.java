package sk.kadlecek.mle.ml.factory;

import sk.kadlecek.mle.ml.bean.ClassifierWithProperties;
import weka.attributeSelection.ASSearch;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.rules.DecisionTable;
import weka.core.SelectedTag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DecisionTableFactory extends AbstractAlgorithmFactory {

    ASSearch[] searchValues = new ASSearch[0];
    boolean[] useIBkValues = new boolean[0];

    @Override
    public ClassifierWithProperties[] generateAllClassifiers() {
        int[][] valueArrayIndexesArray = generateArrayIndexCartesianProduct(
                searchValues.length, useIBkValues.length
        );

        List<ClassifierWithProperties> classifiers = new ArrayList<>();

        for (int i = 0; i < valueArrayIndexesArray.length; i++) {
            int[] valueArrayIndexes = valueArrayIndexesArray[i];

            DecisionTable classifier = new DecisionTable();
            Map<String, String> properties = new TreeMap<>();

            if (valueArrayIndexes[0] > -1) {
                ASSearch value = searchValues[valueArrayIndexes[0]];
                properties.put("SearchMethod", value.toString().replace("\n", "").replace("\r", ""));
                classifier.setSearch(value);
            }

            if (valueArrayIndexes[1] > -1) {
                Boolean value = useIBkValues[valueArrayIndexes[1]];
                properties.put("UseIBk", value.toString());
                classifier.setUseIBk(value);
            }

            classifiers.add(new ClassifierWithProperties(classifier, properties));
        }

        return classifiers.toArray(new ClassifierWithProperties[classifiers.size()]);
    }

    public void setSearchValues(ASSearch[] searchValues) {
        this.searchValues = searchValues;
    }

    public void setUseIBkValues(boolean[] useIBkValues) {
        this.useIBkValues = useIBkValues;
    }
}
