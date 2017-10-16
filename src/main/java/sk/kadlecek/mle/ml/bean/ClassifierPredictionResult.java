package sk.kadlecek.mle.ml.bean;

import weka.core.Instances;

import java.util.LinkedHashMap;
import java.util.Map;

public class ClassifierPredictionResult implements ClassifierRunnableResult {

    private Integer classifierId;
    private String classifierName;
    private Map<String, String> properties = new LinkedHashMap<>();

    private Instances classifiedDataset;


    public ClassifierPredictionResult() { super(); }

    public ClassifierPredictionResult(Integer classifierId, String classifierName, Map<String, String> properties, Instances classifiedDataset) {
        this.classifierId = classifierId;
        this.classifierName = classifierName;
        this.properties = properties;
        this.classifiedDataset = classifiedDataset;
    }

    @Override
    public Integer getClassifierId() {
        return classifierId;
    }

    public void setClassifierId(Integer classifierId) {
        this.classifierId = classifierId;
    }

    public String getClassifierName() {
        return classifierName;
    }

    public void setClassifierName(String classifierName) {
        this.classifierName = classifierName;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public Instances getClassifiedDataset() {
        return classifiedDataset;
    }

    public void setClassifiedDataset(Instances classifiedDataset) {
        this.classifiedDataset = classifiedDataset;
    }
}
