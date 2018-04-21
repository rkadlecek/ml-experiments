package sk.kadlecek.mle;

import org.apache.commons.cli.*;
import sk.kadlecek.mle.ml.AbstractEvaluation;
import sk.kadlecek.mle.ml.bean.AlgorithmStats;
import sk.kadlecek.mle.ml.bean.ClassifierWithProperties;
import sk.kadlecek.mle.ml.utils.CommandLineUtils;
import weka.core.Instances;

import java.util.Map;

import static sk.kadlecek.mle.ml.Common.readDataFile;

public class CrossValidationEvaluation extends AbstractEvaluation {

    private static final String DATASET_OPT = "data-set";
    private static final String NUMBER_OF_FOLDS_OPT = "number-of-folds";
    private static final String NUMBER_OF_RUNS_OPT = "number-of-runs";

    public static void main(String[] args) throws Exception {
        // parse commandline arguments
        Options options = defineCommandlineOptions();
        CommandLine commandLine = CommandLineUtils.parseCommandLineArgs(options, args);

        CommandLineUtils.printHelpAndDieIfRequired("CrossfoldEvaluation", commandLine, options);

        Instances dataset = readDataFile(commandLine.getOptionValue(DATASET_OPT));

        int numberOfFolds = CommandLineUtils.parseIntOption(commandLine, NUMBER_OF_FOLDS_OPT, 10);
        int numberOfRuns = CommandLineUtils.parseIntOption(commandLine, NUMBER_OF_RUNS_OPT, 1);

        ClassifierWithProperties[] models = buildClassifiers(commandLine);
        Map<Integer, AlgorithmStats> evaluationResults = crossValidationEvaluateClassifiers(models, dataset, numberOfFolds, numberOfRuns);
        printAlgorithmStats(evaluationResults.values());
    }

    private static Options defineCommandlineOptions() {
        Options options = CommandLineUtils.defineCommonAndDefaultAndBestConfigurationOnlyOptions();

        Option dataset = Option.builder("d")
                .longOpt(DATASET_OPT)
                .desc("path to data set file")
                .hasArg()
                .argName("dataset-file-path")
                .optionalArg(false)
                .build();

        Option numOfFolds = Option.builder("f")
                .longOpt(NUMBER_OF_FOLDS_OPT)
                .desc("Set number of folds for the crossfold evaluation")
                .hasArg()
                .argName("number_of_folds")
                .optionalArg(true)
                .build();

        Option numOfRuns = Option.builder("r")
                .longOpt(NUMBER_OF_RUNS_OPT)
                .desc("Set number of runs of the crossfold evaluation")
                .hasArg()
                .argName("number_of_runs")
                .optionalArg(true)
                .build();

        options.addOption(dataset);
        options.addOption(numOfFolds);
        options.addOption(numOfRuns);
        return options;
    }

}
