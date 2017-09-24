package sk.kadlecek.mle.preprocessing.output;

import sk.kadlecek.mle.preprocessing.featuredetection.InputValueWithFeatures;
import sk.kadlecek.mle.preprocessing.input.InputValue;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class TxtOutputWriter {

    public void write(List<InputValueWithFeatures> features, String fileName, char separator, boolean appendClass) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

        Set<String> classesSet = new HashSet<>();
        for (InputValueWithFeatures feature : features) {
            if (feature.getClazz() != null) {
                classesSet.add(feature.getClazz());
            }
        }
        List<String> classesList = new ArrayList<>();
        classesList.addAll(classesSet);
        writeHeader(writer, classesList);

        for (int i = 0; i < features.size(); i++) {
            InputValueWithFeatures feature = features.get(i);
            writer.write(feature.toCsvString(separator, appendClass));
            if (i < features.size() - 1) {
                writer.write("\n");
            }
        }

        writer.close();
    }

    public void writeInputValues(InputValue[] inputValues, String fileName, char separator) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

        Set<String> classesSet = new HashSet<>();
        for (InputValue value : inputValues) {
            if (value.getClazz() != null) {
                classesSet.add(value.getClazz());
            }
        }
        List<String> classesList = new ArrayList<>();
        classesList.addAll(classesSet);
        writeInputValuesHeader(writer, classesList);

        for (int i = 0; i < inputValues.length; i++) {
            InputValue value = inputValues[i];
            String valueStr = value.getInputValue().replaceAll("\"", "'");

            writer.write('"' + valueStr + '"' + separator + value.getClazz());

            if (i < inputValues.length - 1) {
                writer.write("\n");
            }
        }

        writer.close();
    }

    private void writeInputValuesHeader(BufferedWriter writer, List<String> classes) throws IOException {
        writer.write("@RELATION products\n\n");
        writer.write("@ATTRIBUTE value STRING\n");
        writer.write("@ATTRIBUTE class {");
        for (int i = 0; i < classes.size(); i++) {
            writer.write(classes.get(i));
            if (i < classes.size() -1) {
                writer.write(",");
            }else {
                writer.write("}\n\n");
            }
        }
        writer.write("@data\n");
    }

    private void writeHeader(BufferedWriter writer, List<String> classes) throws IOException {
        writer.write("@RELATION products\n\n");
        writer.write("@ATTRIBUTE numberOfWords NUMERIC\n");
        writer.write("@ATTRIBUTE numberOfTotalCharacters NUMERIC\n");
        writer.write("@ATTRIBUTE numberOfAlphabetCharacters NUMERIC\n");
        writer.write("@ATTRIBUTE numberOfNumericCharacters NUMERIC\n");
        writer.write("@ATTRIBUTE numberOfWhiteSpaceCharacters NUMERIC\n");
        writer.write("@ATTRIBUTE numberOfSpecialCharacters NUMERIC\n");
        writer.write("@ATTRIBUTE isNumericValue {true, false}\n");
        //writer.write("@ATTRIBUTE isIntegralNumericValue {true, false}\n");
        writer.write("@ATTRIBUTE isDecimalNumericValue {true, false}\n");
        writer.write("@ATTRIBUTE isPrefixedNumber {true, false}\n");
        writer.write("@ATTRIBUTE isPostfixedNumber {true, false}\n");
        writer.write("@ATTRIBUTE class {");
        for (int i = 0; i < classes.size(); i++) {
            writer.write(classes.get(i));
            if (i < classes.size() -1) {
                writer.write(",");
            }else {
                writer.write("}\n\n");
            }
        }
        writer.write("@data\n");
    }

}
