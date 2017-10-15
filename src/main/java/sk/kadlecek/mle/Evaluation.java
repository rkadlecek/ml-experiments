package sk.kadlecek.mle;

import org.apache.commons.cli.*;
import sk.kadlecek.mle.ml.AbstractEvaluation;
import sk.kadlecek.mle.ml.bean.ClassifierWithProperties;
import sk.kadlecek.mle.ml.utils.CommandLineUtils;
import weka.core.Instances;

import static sk.kadlecek.mle.ml.Common.*;

public class Evaluation extends AbstractEvaluation {

    private static final String TRAINING_SET_OPT = "training-set";
    private static final String TESTING_SET_OPT = "testing-set";
    private static final String VECTORIZE_STRINGS_OPT = "vectorize-strings";


    public static void main(String[] args) throws Exception {
        // parse commandline arguments
        Options options = defineCommandlineOptions();
        CommandLine commandLine = CommandLineUtils.parseCommandLineArgs(options, args);

        CommandLineUtils.printHelpAndDieIfRequired("Evaluation", commandLine, options);

        Instances trainingData = readDataFile(commandLine.getOptionValue(TRAINING_SET_OPT));
        Instances testingData = readDataFile(commandLine.getOptionValue(TESTING_SET_OPT));

        boolean vectorizeStrings = commandLine.hasOption(VECTORIZE_STRINGS_OPT);

        ClassifierWithProperties[] models = buildClassifiers(commandLine);
        evaluateClassifiers(models, trainingData, testingData, vectorizeStrings);
    }

    private static Options defineCommandlineOptions() {
        Options options = CommandLineUtils.defineCommonCommandlineOptions();

        Option trainingSet = Option.builder("t")
                .longOpt(TRAINING_SET_OPT)
                .desc("path to training set file")
                .hasArg()
                .argName("training-set-file-path")
                .optionalArg(false)
                .build();

        Option testingSet = Option.builder("s")
                .longOpt(TESTING_SET_OPT)
                .desc("path to testing set file")
                .hasArg()
                .argName("testing-set-file-path")
                .optionalArg(false)
                .build();

        Option vectorizeStrings = Option.builder("v")
                .longOpt(VECTORIZE_STRINGS_OPT)
                .desc("vectorize strings (if input value is a string instead of set of features)")
                .build();

        options.addOption(trainingSet);
        options.addOption(testingSet);
        options.addOption(vectorizeStrings);
        return options;
    }

}
