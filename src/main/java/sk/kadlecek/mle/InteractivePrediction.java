package sk.kadlecek.mle;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import sk.kadlecek.mle.ml.AbstractEvaluation;
import sk.kadlecek.mle.ml.bean.ClassifierWithProperties;
import sk.kadlecek.mle.ml.utils.CommandLineUtils;
import sk.kadlecek.mle.preprocessing.featuredetection.InputValueWithFeatures;
import sk.kadlecek.mle.preprocessing.featuredetection.TextFeatureDetector;
import sk.kadlecek.mle.preprocessing.input.InputValue;
import weka.core.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import static sk.kadlecek.mle.ml.Common.readDataFile;

public class InteractivePrediction extends AbstractEvaluation {

    public static void main(String[] args) throws Exception {
        // parse commandline arguments
        Options options = defineCommandlineOptions();
        CommandLine commandLine = CommandLineUtils.parseCommandLineArgs(options, args);

        CommandLineUtils.printHelpAndDieIfRequired("InteractivePrediction", commandLine, options);

        Instances trainingData = readDataFile(CommandLineUtils.getTrainingDataset(commandLine));

        ClassifierWithProperties cwp = buildBestClassifier(commandLine);
        // build classifier
        cwp.getClassifier().buildClassifier(trainingData);

        //receive inputs from commandline and predict classes
        predictInteractively(cwp);
    }

    private static Options defineCommandlineOptions() {
        return CommandLineUtils.defineCommonAndTrainingDatasetOptions();
    }

    private static void predictInteractively(ClassifierWithProperties cwp) throws Exception {
        System.out.println("Classificating using " + cwp.getClassifierSimpleName() + ". Type 'exit' to exit.");

        Scanner scanner = new Scanner(System.in);
        TextFeatureDetector featureDetector = new TextFeatureDetector();

        while (true) {
            System.out.print("Enter input to classify: ");
            String input = scanner.nextLine();

            if (input.equals("exit")) {
                System.exit(0);
            }
            InputValue inputValue = new InputValue(input, "");
            InputValueWithFeatures vwf = featureDetector.calculateFeatures(inputValue);

            Instance instance = convertInputValueToInstance(vwf).instance(0);
            double label = cwp.getClassifier().classifyInstance(instance);
            System.out.println("Label: " + label);
            instance.setClassValue(label);

            System.out.println("Result[" + inputValue.getInputValue() + "]: " + instance.toString() + " " +
                    instance.classAttribute().value((int) label) + "\n");

            /*double[] scores = cwp.getClassifier().distributionForInstance(instance);
            System.out.println("Result[" + inputValue.getInputValue() + "]: \n");
            for (int i = 0; i < scores.length; i++) {
                double score = scores[i];
                System.out.println("\t " + i + ": " + score);
            }*/
        }
    }

    private static Instances convertInputValueToInstance(InputValueWithFeatures iv) {
        // Declare two numeric attributes
        ArrayList<Attribute> fvWekaAttributes = new ArrayList<>(12);

        fvWekaAttributes.add(new Attribute("numberOfWords"));
        fvWekaAttributes.add(new Attribute("numberOfTotalCharacters"));
        fvWekaAttributes.add(new Attribute("numberOfAlphabetCharacters"));
        fvWekaAttributes.add(new Attribute("numberOfNumericCharacters"));
        fvWekaAttributes.add(new Attribute("numberOfWhiteSpaceCharacters"));
        fvWekaAttributes.add(new Attribute("numberOfSpecialCharacters"));

        ArrayList<String> booleanValues = new ArrayList<>(2);
        booleanValues.add("true");
        booleanValues.add("false");

        fvWekaAttributes.add(new Attribute("isNumericValue", booleanValues));
        fvWekaAttributes.add(new Attribute("isIntegralNumericValue", booleanValues));
        fvWekaAttributes.add(new Attribute("isDecimalNumericValue", booleanValues));
        fvWekaAttributes.add(new Attribute("isPrefixedNumber", booleanValues));
        fvWekaAttributes.add(new Attribute("isPostfixedNumber", booleanValues));

        ArrayList<String> classValues = new ArrayList<>();
        String[] classes = ("BLUETOOTH_VERSION,RAM_TYPE,CPU_FREQ,CPU,OS_BITS,WIRELESS_CONNECTIVITY,CAMERA_BACK," +
                "BATTERY_CAPACITY,NETWORK_4G,GPU,NETWORK_2G,BATTERY_TYPE,STORAGE_SSD,RAM_CAPACITY,EXTERNAL_MEMORY," +
                "COLOR,WEIGHT,DEVICE_TYPE,OS,PRICE,CAMERA_FRONT,SIM_CARD_SLOT,SCREEN_TYPE,PRODUCT_SIZE,SCREEN_PPI," +
                "NETWORK_3G,SIM_CARD_TYPE,BRAND,ETHERNET_SPEED,SCREEN_SIZE,NETWORK_TYPE,SCREEN_RESOLUTION,CPU_CORES," +
                "STORAGE_HDD,TITLE,WIFI,USB_VERSION").split(",");

        Collections.addAll(classValues, classes);
        fvWekaAttributes.add(new Attribute("class", classValues));

        Instances instances = new Instances("Rel", fvWekaAttributes, 1);
        instances.setClassIndex(instances.numAttributes() - 1);

        Instance instance = new DenseInstance(12);
        instance.setValue(fvWekaAttributes.get(0), iv.getNumberOfWords());
        instance.setValue(fvWekaAttributes.get(1), iv.getNumberOfTotalCharacters());
        instance.setValue(fvWekaAttributes.get(2), iv.getNumberOfAlphabetCharacters());
        instance.setValue(fvWekaAttributes.get(3), iv.getNumberOfNumericCharacters());
        instance.setValue(fvWekaAttributes.get(4), iv.getNumberOfWhiteSpaceCharacters());
        instance.setValue(fvWekaAttributes.get(5), iv.getNumberOfSpecialCharacters());
        instance.setValue(fvWekaAttributes.get(6), String.valueOf(iv.isNumericValue()));
        instance.setValue(fvWekaAttributes.get(7), String.valueOf(iv.isIntegralNumericValue()));
        instance.setValue(fvWekaAttributes.get(8), String.valueOf(iv.isDecimalNumericValue()));
        instance.setValue(fvWekaAttributes.get(9), String.valueOf(iv.isPrefixedNumber()));
        instance.setValue(fvWekaAttributes.get(10), String.valueOf(iv.isPostfixedNumber()));
        instance.setValue(fvWekaAttributes.get(11), Utils.missingValue());
        instances.add(instance);
        return instances;
    }
}
