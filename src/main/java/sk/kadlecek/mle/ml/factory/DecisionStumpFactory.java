package sk.kadlecek.mle.ml.factory;

import sk.kadlecek.mle.ml.bean.ClassifierWithProperties;
import weka.classifiers.trees.DecisionStump;

import java.util.HashMap;

public class DecisionStumpFactory extends AbstractAlgorithmFactory {

    @Override
    public ClassifierWithProperties[] generateAllClassifiers() {
        // DecisionStump algorithm contains no configuration options,
        // return just default one
        ClassifierWithProperties[] defaultStump = new ClassifierWithProperties[1];
        defaultStump[0] = new ClassifierWithProperties(new DecisionStump(), new HashMap<>());
        return defaultStump;
    }

}
