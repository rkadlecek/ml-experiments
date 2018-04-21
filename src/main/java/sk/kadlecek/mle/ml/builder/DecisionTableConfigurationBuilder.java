package sk.kadlecek.mle.ml.builder;

import sk.kadlecek.mle.ml.bean.ClassifierWithProperties;
import sk.kadlecek.mle.ml.factory.AbstractAlgorithmFactory;
import sk.kadlecek.mle.ml.factory.DecisionTableFactory;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.BestFirst;
import weka.attributeSelection.GreedyStepwise;
import weka.core.SelectedTag;

import static sk.kadlecek.mle.ml.Common.generateBooleanRange;

public class DecisionTableConfigurationBuilder extends BaseClassifierConfigurationBuilder {

    @Override
    public ClassifierWithProperties[] buildClassifiers() {
        // build classifiers
        DecisionTableFactory factory = (DecisionTableFactory) getFactory();
        boolean[] booleanRange = generateBooleanRange();

        ASSearch[] searchMethods = {
                new BestFirst() {{ setDirection(new SelectedTag(SELECTION_FORWARD, TAGS_SELECTION)); }},
                new BestFirst() {{ setDirection(new SelectedTag(SELECTION_BACKWARD, TAGS_SELECTION)); }},
                new BestFirst() {{ setDirection(new SelectedTag(SELECTION_BIDIRECTIONAL, TAGS_SELECTION)); }},
                //new GreedyStepwise(),
                new GreedyStepwise() {{ setSearchBackwards(true);}}
        };

        factory.setSearchValues(searchMethods);
        factory.setUseIBkValues(booleanRange);

        // evaluate
        return factory.generateAllClassifiers();
    }

    @Override
    public ClassifierWithProperties bestConfiguration() {
        return null;
    }

    @Override
    public AbstractAlgorithmFactory getFactory() {
        return new DecisionTableFactory();
    }
}
