package sk.kadlecek.mle;

import org.apache.commons.cli.*;
import sk.kadlecek.mle.ml.AbstractEvaluation;
import sk.kadlecek.mle.ml.bean.ClassifierWithProperties;
import sk.kadlecek.mle.ml.builder.*;
import weka.core.Instances;

import static sk.kadlecek.mle.ml.Common.*;

public class Evaluation extends AbstractEvaluation {

    private static final String ALGORITHM_OPT = "algorithm";
    private static final String BEST_CONFIGURATION_ONLY_OPT = "best-config-only";
    private static final String TRAINING_SET_OPT = "training-set";
    private static final String TESTING_SET_OPT = "testing-set";
    private static final String HELP_OPT = "help";
    private static final String VECTORIZE_STRINGS_OPT = "vectorize-strings";


    public static void main(String[] args) throws Exception {
        // parse commandline arguments
        Options options = defineCommandlineOptions();
        CommandLine commandLine = parseCommandLineArgs(options, args);

        if (commandLine.hasOption(HELP_OPT)) {
            printHelp(options);
            System.exit(0);
        }

        boolean vectorizeStrings = commandLine.hasOption(VECTORIZE_STRINGS_OPT);

        Instances trainingData = readDataFile(commandLine.getOptionValue(TRAINING_SET_OPT));
        Instances testingData = readDataFile(commandLine.getOptionValue(TESTING_SET_OPT));

        String algorithm = commandLine.getOptionValue(ALGORITHM_OPT);
        ClassifierConfigurationBuilder builder = getBuilderForAlgorithm(algorithm);

        ClassifierWithProperties[] models = builder.buildClassifiers();
        if (commandLine.hasOption(BEST_CONFIGURATION_ONLY_OPT)) {
            models = new ClassifierWithProperties[]{ builder.bestConfiguration() };
        }
        evaluateClassifiers(models, trainingData, testingData, vectorizeStrings);
    }

    private static Options defineCommandlineOptions() {
        Options options = new Options();

        Option algorithm = Option.builder("a")
                .longOpt(ALGORITHM_OPT)
                .desc("algorithm to evaluate (J48, DecisionStump, DecisionTable, PART, RandomForest, NaiveBayes, SMO, MultilayerPerceptron)")
                .hasArg()
                .argName("algorithm")
                .optionalArg(false)
                .build();

        Option bestConfigOnly = Option.builder("b")
                .longOpt(BEST_CONFIGURATION_ONLY_OPT)
                .desc("Use only best algorithm configuration for the same algorithm, do not evaluate multiple algorithm configurations")
                .build();

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

        Option help = Option.builder("h")
                .longOpt(HELP_OPT)
                .desc("print help")
                .build();

        options.addOption(algorithm);
        options.addOption(bestConfigOnly);
        options.addOption(trainingSet);
        options.addOption(testingSet);
        options.addOption(help);
        options.addOption(vectorizeStrings);
        return options;
    }

    private static CommandLine parseCommandLineArgs(Options options, String[] args) throws IllegalArgumentException {
        // create the parser
        CommandLineParser parser = new DefaultParser();
        try {
            // parse the command line arguments
            return parser.parse(options, args);
        }
        catch( ParseException e ) {
            throw new IllegalArgumentException( "Failed to parse commandline arguments: " + e.getMessage());
        }
    }

    private static void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp( "Evaluation", options );
    }

    private static ClassifierConfigurationBuilder getBuilderForAlgorithm(String algorithm) throws IllegalArgumentException {
        switch (algorithm) {
            case "J48":
                return new J48ConfigurationBuilder();
            case "DecisionStump":
                return new DecisionStumpConfigurationBuilder();
            case "DecisionTable":
                return new DecisionTableConfigurationBuilder();
            case "PART":
                return new PARTConfigurationBuilder();
            case "RandomForest":
                return new RandomForestConfigurationBuilder();
            case "NaiveBayes":
                return new NaiveBayesConfigurationBuilder();
            case "SMO":
                return new SMOConfigurationBuilder();
            case "MultilayerPerceptron":
                return new MultilayerPerceptronConfigurationBuilder();
            default:
                throw new IllegalArgumentException("Unknown algorithm: " + algorithm);
        }
    }

}
