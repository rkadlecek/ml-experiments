package sk.kadlecek.mle.ml.bean;

import weka.classifiers.Classifier;

import java.util.Map;
import java.util.TreeMap;

public class ClassifierWithProperties {

    private Classifier classifier;
    private Map<String, String> properties = new TreeMap<>();

    public ClassifierWithProperties(Classifier classifier, Map<String, String> properties) {
        this.classifier = classifier;
        this.properties = properties;
    }

    public String getClassifierSimpleName() {
        return classifier.getClass().getSimpleName();
    }

    public Classifier getClassifier() {
        return classifier;
    }

    public void setClassifier(Classifier classifier) {
        this.classifier = classifier;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
}
