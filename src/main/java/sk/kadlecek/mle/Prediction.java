package sk.kadlecek.mle;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import sk.kadlecek.mle.ml.AbstractEvaluation;
import sk.kadlecek.mle.ml.bean.ClassifierPredictionResult;
import sk.kadlecek.mle.ml.bean.ClassifierWithProperties;
import sk.kadlecek.mle.ml.utils.CommandLineUtils;
import weka.core.Instances;

import java.util.Map;

import static sk.kadlecek.mle.ml.Common.readDataFile;

public class Prediction extends AbstractEvaluation {

    private static final String OUTPUT_PATH = "output-path";

    public static void main(String[] args) throws Exception {
        // parse commandline arguments
        Options options = defineCommandlineOptions();
        CommandLine commandLine = CommandLineUtils.parseCommandLineArgs(options, args);

        CommandLineUtils.printHelpAndDieIfRequired("Evaluation", commandLine, options);

        Instances trainingData = readDataFile(CommandLineUtils.getTrainingDataset(commandLine));
        Instances testingData = readDataFile(CommandLineUtils.getTestingDataset(commandLine));

        String outputPath = commandLine.getOptionValue(OUTPUT_PATH);

        ClassifierWithProperties[] models = buildClassifiers(commandLine);
        Map<Integer, ClassifierPredictionResult> predictionResults = predictUsingClassifiers(models, trainingData, testingData);
        savePredictionResults(predictionResults, outputPath);
    }


    private static Options defineCommandlineOptions() {
        Options options = CommandLineUtils.defineCommonAndTrainingTestingDatasetOptions();
        Option vectorizeStrings = Option.builder("o")
                .longOpt(OUTPUT_PATH)
                .desc("Absolute Path (directory) on the filesystem, where the result file(s) will be stored.)")
                .hasArg()
                .argName("output-path")
                .optionalArg(false)
                .build();

        options.addOption(vectorizeStrings);
        return options;
    }

}
