package sk.kadlecek.mle.preprocessing.featuredetection;

import sk.kadlecek.mle.preprocessing.input.InputValue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextFeatureDetector implements FeatureDetector {

    public InputValueWithFeatures calculateFeatures(InputValue inputValue) {
        InputValueWithFeatures features = new InputValueWithFeatures();

        String inputValueTrimmed = inputValue.getInputValue().trim();

        features.setValue(inputValueTrimmed);
        features.setClazz(inputValue.getClazz());

        int numberOfWords = 0;
        int numberOfTotalCharacters = inputValueTrimmed.length();
        int numberOfAlphabetCharacters = 0;
        int numberOfNumericCharacters = 0;
        int numberOfWhitespaceCharacters = 0;
        int numberOfSpecialCharacters = 0;

        boolean inWord = false;
        for (int i = 0; i < inputValueTrimmed.length(); i++) {

            char ch = inputValueTrimmed.charAt(i);

            if (Character.isLetter(ch)) {
                numberOfAlphabetCharacters++;
                if (!inWord) inWord = true;
            }
            else if (Character.isDigit(ch)) {
                if (!inWord) inWord = true;
                numberOfNumericCharacters++;
            }
            else if (Character.isWhitespace(ch)) {
                if (inWord) {
                    inWord = false;
                    numberOfWords++;
                }
                numberOfWhitespaceCharacters++;
            }
            else numberOfSpecialCharacters++;
        }
        if (inWord) {
            numberOfWords++;
        }

        features.setNumberOfWords(numberOfWords);
        features.setNumberOfTotalCharacters(numberOfTotalCharacters);
        features.setNumberOfAlphabetCharacters(numberOfAlphabetCharacters);
        features.setNumberOfNumericCharacters(numberOfNumericCharacters);
        features.setNumberOfWhiteSpaceCharacters(numberOfWhitespaceCharacters);
        features.setNumberOfSpecialCharacters(numberOfSpecialCharacters);
        try {
            long longValue = Long.parseLong(inputValueTrimmed);
            features.setIntegralNumericValue(true);
        }catch (NumberFormatException e) {
            try {
                double doubleValue = Double.parseDouble(inputValueTrimmed);
                features.setDecimalNumericValue(true);
            }catch (NumberFormatException ex) {
                //ignore
            }
        }
        features.setPrefixedNumber(isPrefixedNumber(inputValueTrimmed));
        features.setPostfixedNumber(isPostfixedNumber(inputValueTrimmed));
        return features;
    }

    private boolean isPrefixedNumber(String inputValue) {
        return regexMatches("^[^0-9]+[0-9]+[,.]{0,1}[0-9]*$", inputValue);
    }

    private boolean isPostfixedNumber(String inputValue) {
        return regexMatches("^[0-9]+[,.]{0,1}[0-9]*[^0-9]+$", inputValue);
    }

    private boolean regexMatches(String regex, String str) {
        Pattern pat = Pattern.compile(regex);
        Matcher mat = pat.matcher(str);
        return mat.matches();
    }

}
