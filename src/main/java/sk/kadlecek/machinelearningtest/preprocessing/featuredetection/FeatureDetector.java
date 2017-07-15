package sk.kadlecek.machinelearningtest.preprocessing.featuredetection;

import sk.kadlecek.machinelearningtest.preprocessing.input.InputValue;

public interface FeatureDetector {

    InputValueWithFeatures calculateFeatures(InputValue inputValue);

}
