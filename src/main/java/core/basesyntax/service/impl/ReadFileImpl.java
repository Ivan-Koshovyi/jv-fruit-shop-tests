package core.basesyntax.service.impl;

import core.basesyntax.service.ReadFile;

import java.io.*;
import java.util.List;

public class ReadFileImpl implements ReadFile {
    @Override
    public List<String> read(String fileName) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            return bufferedReader.lines()
                    .skip(1)
                    .toList();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: " + fileName, e);
        } catch (IOException e) {
            throw new RuntimeException("Error while reading file: " + fileName, e);
        }
    }
}
