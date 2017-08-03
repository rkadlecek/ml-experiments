package sk.kadlecek.mle.preprocessing.featuredetection;

import sk.kadlecek.mle.preprocessing.input.InputValue;

public interface FeatureDetector {

    InputValueWithFeatures calculateFeatures(InputValue inputValue);

}
