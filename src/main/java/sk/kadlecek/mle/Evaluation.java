package sk.kadlecek.mle;

import org.apache.commons.cli.*;
import sk.kadlecek.mle.ml.AbstractEvaluation;
import sk.kadlecek.mle.ml.bean.AlgorithmStats;
import sk.kadlecek.mle.ml.bean.ClassifierWithProperties;
import sk.kadlecek.mle.ml.utils.CommandLineUtils;
import weka.core.Instances;

import java.util.Map;

import static sk.kadlecek.mle.ml.Common.*;

public class Evaluation extends AbstractEvaluation {

    private static final String VECTORIZE_STRINGS_OPT = "vectorize-strings";


    public static void main(String[] args) throws Exception {
        // parse commandline arguments
        Options options = defineCommandlineOptions();
        CommandLine commandLine = CommandLineUtils.parseCommandLineArgs(options, args);

        CommandLineUtils.printHelpAndDieIfRequired("Evaluation", commandLine, options);

        Instances trainingData = readDataFile(CommandLineUtils.getTrainingDataset(commandLine));
        Instances testingData = readDataFile(CommandLineUtils.getTestingDataset(commandLine));

        boolean vectorizeStrings = commandLine.hasOption(VECTORIZE_STRINGS_OPT);

        ClassifierWithProperties[] models = buildClassifiers(commandLine);
        Map<Integer, AlgorithmStats> evaluationResults = evaluateClassifiers(models, trainingData, testingData, vectorizeStrings);
        printAlgorithmStats(evaluationResults.values());
    }

    private static Options defineCommandlineOptions() {
        Options options = CommandLineUtils.defineCommonAndTrainingTestingDatasetOptions();

        Option vectorizeStrings = Option.builder("v")
                .longOpt(VECTORIZE_STRINGS_OPT)
                .desc("vectorize strings (if input value is a string instead of set of features)")
                .build();

        options.addOption(vectorizeStrings);
        return options;
    }

}
