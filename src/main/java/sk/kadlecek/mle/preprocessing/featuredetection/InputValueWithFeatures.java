package sk.kadlecek.mle.preprocessing.featuredetection;

import weka.core.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class InputValueWithFeatures {

    private String value;

    private int numberOfWords = 0;
    private int numberOfTotalCharacters = 0;
    private int numberOfAlphabetCharacters = 0;
    private int numberOfNumericCharacters = 0;
    private int numberOfWhiteSpaceCharacters = 0;
    private int numberOfSpecialCharacters = 0;
    private boolean isNumericValue = false;
    private boolean isIntegralNumericValue = false;
    private boolean isDecimalNumericValue = false;
    private boolean isPrefixedNumber = false;
    private boolean isPostfixedNumber = false;

    private String clazz;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getNumberOfWords() {
        return numberOfWords;
    }

    public void setNumberOfWords(int numberOfWords) {
        this.numberOfWords = numberOfWords;
    }

    public int getNumberOfTotalCharacters() {
        return numberOfTotalCharacters;
    }

    public void setNumberOfTotalCharacters(int numberOfTotalCharacters) {
        this.numberOfTotalCharacters = numberOfTotalCharacters;
    }

    public int getNumberOfAlphabetCharacters() {
        return numberOfAlphabetCharacters;
    }

    public void setNumberOfAlphabetCharacters(int numberOfAlphabetCharacters) {
        this.numberOfAlphabetCharacters = numberOfAlphabetCharacters;
    }

    public int getNumberOfNumericCharacters() {
        return numberOfNumericCharacters;
    }

    public void setNumberOfNumericCharacters(int numberOfNumericCharacters) {
        this.numberOfNumericCharacters = numberOfNumericCharacters;
    }

    public int getNumberOfSpecialCharacters() {
        return numberOfSpecialCharacters;
    }

    public void setNumberOfSpecialCharacters(int numberOfSpecialCharacters) {
        this.numberOfSpecialCharacters = numberOfSpecialCharacters;
    }

    public boolean isNumericValue() {
        return isNumericValue;
    }

    public boolean isIntegralNumericValue() {
        return isIntegralNumericValue;
    }

    public void setIntegralNumericValue(boolean integralNumericValue) {
        if (integralNumericValue) isNumericValue = integralNumericValue;
        isIntegralNumericValue = integralNumericValue;
    }

    public boolean isDecimalNumericValue() {
        return isDecimalNumericValue;
    }

    public void setDecimalNumericValue(boolean decimalNumericValue) {
        if (decimalNumericValue) isNumericValue = decimalNumericValue;
        isDecimalNumericValue = decimalNumericValue;
    }

    public int getNumberOfWhiteSpaceCharacters() {
        return numberOfWhiteSpaceCharacters;
    }

    public void setNumberOfWhiteSpaceCharacters(int numberOfWhiteSpaceCharacters) {
        this.numberOfWhiteSpaceCharacters = numberOfWhiteSpaceCharacters;
    }

    public void setNumericValue(boolean numericValue) {
        isNumericValue = numericValue;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public boolean isPrefixedNumber() {
        return isPrefixedNumber;
    }

    public void setPrefixedNumber(boolean prefixedNumber) {
        isPrefixedNumber = prefixedNumber;
    }

    public boolean isPostfixedNumber() {
        return isPostfixedNumber;
    }

    public void setPostfixedNumber(boolean postfixedNumber) {
        isPostfixedNumber = postfixedNumber;
    }

    public String toCsvString(char separator, boolean appendClass) {
        //StringBuilder sb = new StringBuilder(value + separator);
        StringBuilder sb = new StringBuilder();
        sb.append(getNumberOfWords() + "" + separator);
        sb.append(getNumberOfTotalCharacters() + "" + separator);
        sb.append(getNumberOfAlphabetCharacters() + "" + separator);
        sb.append(getNumberOfNumericCharacters() + "" + separator);
        sb.append(getNumberOfWhiteSpaceCharacters() + "" + separator);
        sb.append(getNumberOfSpecialCharacters() + "" + separator);
        sb.append(isNumericValue() + "" + separator);
        //sb.append(isIntegralNumericValue() + "" + separator);
        sb.append(isDecimalNumericValue() + "" + separator);
        sb.append(isPrefixedNumber() + "" + separator);
        sb.append(isPostfixedNumber());
        if (appendClass) {
            sb.append(separator + getClazz());
        }
        return sb.toString();
    }
}
