package sk.kadlecek.mle.ml.utils;

import org.apache.commons.cli.*;

public class CommandLineUtils {

    private static final String ALGORITHM_OPT = "algorithm";
    private static final String BEST_CONFIGURATION_ONLY_OPT = "best-config-only";
    private static final String HELP_OPT = "help";

    public static Options defineCommonCommandlineOptions() {
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

        Option help = Option.builder("h")
                .longOpt(HELP_OPT)
                .desc("print help")
                .build();

        options.addOption(algorithm);
        options.addOption(bestConfigOnly);
        options.addOption(help);
        return options;
    }

    public static boolean hasPrintHelpOption(CommandLine commandLine) {
        return commandLine.hasOption(HELP_OPT);
    }

    public static void printHelpAndDieIfRequired(String name, CommandLine commandLine, Options options) {
        if (hasPrintHelpOption(commandLine)) {
            printHelp(name, options);
            System.exit(0);
        }
    }

    public static boolean hasUseOnlyBestConfigurationOption(CommandLine commandLine) {
        return commandLine.hasOption(BEST_CONFIGURATION_ONLY_OPT);
    }

    public static String getSelectedAlgorithm(CommandLine commandLine) {
        return commandLine.getOptionValue(ALGORITHM_OPT);
    }

    public static void printHelp(String name, Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp( name, options );
    }

    public static CommandLine parseCommandLineArgs(Options options, String[] args) throws IllegalArgumentException {
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

    public static int parseIntOption(CommandLine commandLine, String optionName, int defaultValue) {
        String cliValue = commandLine.getOptionValue(optionName);
        if (cliValue != null) {
            return Integer.parseInt(cliValue);
        } else {
            return defaultValue;
        }
    }
}
