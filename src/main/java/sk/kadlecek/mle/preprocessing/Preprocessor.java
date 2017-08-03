package sk.kadlecek.mle.preprocessing;

import sk.kadlecek.mle.preprocessing.input.CsvInputReader;
import sk.kadlecek.mle.preprocessing.input.InputReader;
import sk.kadlecek.mle.preprocessing.input.InputValue;
import sk.kadlecek.mle.preprocessing.output.TxtOutputWriter;
import sk.kadlecek.mle.preprocessing.featuredetection.TextFeatureDetector;
import sk.kadlecek.mle.preprocessing.featuredetection.InputValueWithFeatures;

import java.util.ArrayList;
import java.util.List;

public class Preprocessor {

    public static void main(String[] args) throws Exception {
        InputReader inputReader = new CsvInputReader();
        InputValue[] values = inputReader.readInputValues("resources/experiment3/me_phones.csv");
        //InputValue[] values = inputReader.readInputValues("preprocessor_sample.csv");

        /*TextFeatureDetector featureDetector = new TextFeatureDetector();

        List<InputValueWithFeatures> featuresList = new ArrayList<>();
        for (InputValue value : values) {
            InputValueWithFeatures features = featureDetector.calculateFeatures(value);
            featuresList.add(features);
        }*/

        TxtOutputWriter writer = new TxtOutputWriter();
        //writer.write(featuresList, "mobileshop_phones_dataset.arff", ',', true);
        //writer.write(featuresList, "preprocessor_output.txt", ',', true);
        writer.writeInputValues(values, "resources/experiment5/me_phones.arff", ',');

        System.out.println("Done");
    }

}
