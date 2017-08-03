package sk.kadlecek.mle.preprocessing.input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CsvInputReader implements InputReader {

    @Override
    public InputValue[] readInputValues(String filename) throws IOException {
        String separator = "\\|";

        List<InputValue> inputValues = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = br.readLine();

        Map<Integer, String> columnHeaderMap = null;
        Set<String> existingValueSet = new HashSet<>();

        boolean isFirstLine = true;

        while (line != null) {
            if (isFirstLine) {
                isFirstLine = false;
                // read first line which contains class names
                columnHeaderMap = parseHeader(line, separator);
            } else {
                inputValues.addAll(parseLine(line, separator, columnHeaderMap, existingValueSet));
            }
            line = br.readLine();
        }

        return inputValues.toArray(new InputValue[0]);
    }

    private Map<Integer, String> parseHeader(String headerLine, String separator) {
        Map<Integer, String> columnHeaderMap = new HashMap<>();
        String[] cols = headerLine.split(separator);
        for (int i = 0; i < cols.length; i++) {
            columnHeaderMap.put(i, cols[i].trim());
        }
        return columnHeaderMap;
    }

    private List<InputValue> parseLine(String line, String separator,
                                       Map<Integer, String> columnHeaderMap, Set<String> existingValueSet) {

        List<InputValue> list = new ArrayList<>();
        String[] cols = line.split(separator);
        int duplicates = 0;
        int emptyvalues = 0;
        for (int i = 0; i < cols.length; i++) {
            String value = cols[i].trim();
            if (!value.isEmpty()) {
                if (!existingValueSet.contains(value)) {
                    list.add(new InputValue(value, columnHeaderMap.get(i)));
                    existingValueSet.add(value);
                } else {
                    duplicates++;
                }
            } else {
                emptyvalues++;
            }
        }

        System.out.println("Skipped " + duplicates + " duplicate and " + emptyvalues + " empty values.");
        return list;
    }
}
