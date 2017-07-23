package sk.kadlecek.mle;

import sk.kadlecek.mle.ml.AbstractEvaluation;
import sk.kadlecek.mle.ml.bean.ClassifierWithProperties;
import sk.kadlecek.mle.ml.factory.DecisionTableFactory;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.BestFirst;
import weka.attributeSelection.GreedyStepwise;
import weka.core.Instances;
import weka.core.SelectedTag;

import static sk.kadlecek.mle.ml.Common.generateBooleanRange;
import static sk.kadlecek.mle.ml.Common.readDataFile;

public class EvaluateDecisionTable extends AbstractEvaluation {

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            printUsageInfo();
            System.exit(1);
        }

        Instances trainingData = readDataFile(args[0]);
        Instances testingData = readDataFile(args[1]);

        // build classifiers
        DecisionTableFactory factory = new DecisionTableFactory();
        boolean[] booleanRange = generateBooleanRange();

        ASSearch[] searchMethods = {
            new BestFirst() {{ setDirection(new SelectedTag(SELECTION_FORWARD, TAGS_SELECTION)); }},
            new BestFirst() {{ setDirection(new SelectedTag(SELECTION_BACKWARD, TAGS_SELECTION)); }},
            new BestFirst() {{ setDirection(new SelectedTag(SELECTION_BIDIRECTIONAL, TAGS_SELECTION)); }},
            new GreedyStepwise(),
            new GreedyStepwise() {{ setSearchBackwards(true);}}
        };

        factory.setSearchValues(searchMethods);
        factory.setUseIBkValues(booleanRange);

        // evaluate
        ClassifierWithProperties[] models = factory.generateAllClassifiers();
        evaluateClassifiers(models, trainingData, testingData);
    }

}
