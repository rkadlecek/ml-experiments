package sk.kadlecek.mle;

import org.apache.commons.cli.*;
import sk.kadlecek.mle.ml.AbstractEvaluation;
import sk.kadlecek.mle.ml.bean.ClassifierWithProperties;
import sk.kadlecek.mle.ml.builder.*;
import weka.core.Instances;

import static sk.kadlecek.mle.ml.Common.readDataFile;

public class CrossValidationEvaluation extends AbstractEvaluation {

    private static final String ALGORITHM_OPT = "algorithm";
    private static final String DATASET_OPT = "data-set";
    private static final String NUMBER_OF_FOLDS_OPT = "number-of-folds";
    private static final String HELP_OPT = "help";

    public static void main(String[] args) throws Exception {
        // parse commandline arguments
        Options options = defineCommandlineOptions();
        CommandLine commandLine = parseCommandLineArgs(options, args);

        if (commandLine.hasOption(HELP_OPT)) {
            printHelp(options);
            System.exit(0);
        }

        Instances dataset = readDataFile(commandLine.getOptionValue(DATASET_OPT));

        String algorithm = commandLine.getOptionValue(ALGORITHM_OPT);
        int numberOfFolds = 10;
        String numberOfFoldsCliValue = commandLine.getOptionValue(NUMBER_OF_FOLDS_OPT);
        if (numberOfFoldsCliValue != null) {
            numberOfFolds = Integer.parseInt(numberOfFoldsCliValue);
        }

        ClassifierConfigurationBuilder builder = getBuilderForAlgorithm(algorithm);

        ClassifierWithProperties[] models = builder.buildClassifiers();
        crossValidationEvaluateClassifiers(models, dataset, numberOfFolds);
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

        Option dataset = Option.builder("d")
                .longOpt(DATASET_OPT)
                .desc("path to data set file")
                .hasArg()
                .argName("dataset-file-path")
                .optionalArg(false)
                .build();

        Option vectorizeStrings = Option.builder("f")
                .longOpt(NUMBER_OF_FOLDS_OPT)
                .desc("Set number of folds for the crossfold evaluation")
                .hasArg()
                .argName("number_of_folds")
                .optionalArg(true)
                .build();

        Option help = Option.builder("h")
                .longOpt(HELP_OPT)
                .desc("print help")
                .build();

        options.addOption(algorithm);
        options.addOption(dataset);
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
        formatter.printHelp( "CrossfoldEvaluation", options );
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
