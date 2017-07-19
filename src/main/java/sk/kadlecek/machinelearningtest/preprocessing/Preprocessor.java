package sk.kadlecek.machinelearningtest.preprocessing;

import sk.kadlecek.machinelearningtest.preprocessing.input.CsvInputReader;
import sk.kadlecek.machinelearningtest.preprocessing.input.InputReader;
import sk.kadlecek.machinelearningtest.preprocessing.input.InputValue;
import sk.kadlecek.machinelearningtest.preprocessing.input.TxtInputReader;
import sk.kadlecek.machinelearningtest.preprocessing.output.TxtOutputWriter;
import sk.kadlecek.machinelearningtest.preprocessing.featuredetection.TextFeatureDetector;
import sk.kadlecek.machinelearningtest.preprocessing.featuredetection.InputValueWithFeatures;

import java.util.ArrayList;
import java.util.List;

public class Preprocessor {

    public static void main(String[] args) throws Exception {
        InputReader inputReader = new CsvInputReader();
        InputValue[] values = inputReader.readInputValues("mobileshop_phones.csv");
        //InputValue[] values = inputReader.readInputValues("preprocessor_sample.csv");

        TextFeatureDetector featureDetector = new TextFeatureDetector();

        List<InputValueWithFeatures> featuresList = new ArrayList<>();
        for (InputValue value : values) {
            InputValueWithFeatures features = featureDetector.calculateFeatures(value);
            featuresList.add(features);
        }

        TxtOutputWriter writer = new TxtOutputWriter();
        writer.write(featuresList, "mobileshop_phones_dataset.arff", ',', true);
        //writer.write(featuresList, "preprocessor_output.txt", ',', true);

        System.out.println("Done");
    }

}
