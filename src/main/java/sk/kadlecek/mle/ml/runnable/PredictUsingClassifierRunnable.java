package sk.kadlecek.mle.ml.runnable;

import sk.kadlecek.mle.ml.bean.ClassifierPredictionResult;
import sk.kadlecek.mle.ml.bean.ClassifierWithProperties;
import weka.classifiers.Classifier;
import weka.core.Instances;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class PredictUsingClassifierRunnable implements Callable<ClassifierPredictionResult>, Supplier<ClassifierPredictionResult> {

    private int id;
    private ClassifierWithProperties modelWithProperties;
    private Instances trainingDataset;
    private Instances testingDataset;

    public  PredictUsingClassifierRunnable(int id, ClassifierWithProperties modelWithProperties,
                                           Instances trainingDataset, Instances testingDataset) {
        this.id = id;
        this.modelWithProperties = modelWithProperties;
        this.trainingDataset = trainingDataset;
        this.testingDataset = testingDataset;
    }

    @Override
    public ClassifierPredictionResult call() throws Exception {
        // create copy of test dataset (for labelling)
        Instances labeled = new Instances(testingDataset);

        // train classifier
        Classifier model = modelWithProperties.getClassifier();
        model.buildClassifier(trainingDataset);

        // label instances
        for (int i = 0; i < testingDataset.numInstances(); i++) {
            double predictedLabel = model.classifyInstance(testingDataset.instance(i));
            labeled.instance(i).setClassValue(predictedLabel);
        }

        return new ClassifierPredictionResult(id, modelWithProperties.getClassifierSimpleName(),
                        modelWithProperties.getProperties(), labeled);
    }

    @Override
    public ClassifierPredictionResult get() {
        try {
            return call();
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
