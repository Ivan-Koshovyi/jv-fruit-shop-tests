package core.basesyntax.service.impl;

import core.basesyntax.service.ReadFile;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ReadFileImpl implements ReadFile {
    @Override
    public List<String> read(String fileName) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            return bufferedReader.lines()
                    .skip(1)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException("File not found: " + fileName, e);
        }
    }
}
