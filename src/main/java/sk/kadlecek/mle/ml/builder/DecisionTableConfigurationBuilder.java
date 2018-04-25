package sk.kadlecek.mle.ml.builder;

import sk.kadlecek.mle.ml.bean.ClassifierWithProperties;
import sk.kadlecek.mle.ml.factory.AbstractAlgorithmFactory;
import sk.kadlecek.mle.ml.factory.DecisionTableFactory;
import weka.attributeSelection.*;
import weka.core.SelectedTag;
import weka.core.Tag;

import static sk.kadlecek.mle.ml.Common.generateBooleanRange;

public class DecisionTableConfigurationBuilder extends BaseClassifierConfigurationBuilder {

    @Override
    public ClassifierWithProperties[] buildClassifiers() {
        // build classifiers
        DecisionTableFactory factory = (DecisionTableFactory) getFactory();
        boolean[] booleanRange = generateBooleanRange();

        ASSearch bestFirstBackward = new BestFirst();
        ((BestFirst) bestFirstBackward).setDirection(new SelectedTag(0, BestFirst.TAGS_SELECTION));

        ASSearch bestFirstForward = new BestFirst();
        ((BestFirst) bestFirstForward).setDirection(new SelectedTag(1, BestFirst.TAGS_SELECTION));

        ASSearch bestFirstBidirectional = new BestFirst();
        ((BestFirst) bestFirstBidirectional).setDirection(new SelectedTag(2, BestFirst.TAGS_SELECTION));

        ASSearch greedyStepWiseForward = new GreedyStepwise();
        ((GreedyStepwise) greedyStepWiseForward).setSearchBackwards(false);

        ASSearch greedyStepWiseBackward = new GreedyStepwise();
        ((GreedyStepwise) greedyStepWiseBackward).setSearchBackwards(true);

        ASSearch[] searchMethods = {
                bestFirstBackward, bestFirstForward, bestFirstBidirectional,
                greedyStepWiseBackward, greedyStepWiseForward
        };

        factory.setSearchValues(searchMethods);
        factory.setUseIBkValues(booleanRange);

        // evaluate
        return factory.generateAllClassifiers();
    }

    @Override
    public ClassifierWithProperties bestConfiguration() {
        DecisionTableFactory factory = (DecisionTableFactory) getFactory();

        ASSearch greedyStepWiseBackward = new GreedyStepwise();
        ((GreedyStepwise) greedyStepWiseBackward).setSearchBackwards(true);

        ASSearch[] useSearches = { greedyStepWiseBackward };
        boolean[] useIbkValues = { true };

        factory.setSearchValues(useSearches);
        factory.setUseIBkValues(useIbkValues);

        return factory.generateAllClassifiers()[0];
    }

    @Override
    public AbstractAlgorithmFactory getFactory() {
        return new DecisionTableFactory();
    }
}
