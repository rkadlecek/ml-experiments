package sk.kadlecek.machinelearningtest.preprocessing.input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TxtInputReader {

    public String[] read(String fileName) throws IOException {
        List<String> lines = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();

        while (line != null) {
            lines.add(line.trim());
            line = br.readLine();
        }
        return lines.toArray(new String[0]);
    }

}
