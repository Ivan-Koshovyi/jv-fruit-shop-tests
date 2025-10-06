package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static FileWriter fileWriter;

    @BeforeAll
    static void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void writeInFile_Ok() {
        try {
            Path tempFile = Files.createTempFile("testReport", ".txt");
            String content = "fruit, quantity\napple, 10\nbanana, 5";
            fileWriter.write(content, String.valueOf(tempFile));
            String fileContent = Files.readString(Path.of(String.valueOf(tempFile)));
            assertEquals(content, fileContent);
            assertTrue(fileContent.contains("apple") && fileContent.contains("banana"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void writeInFile_NotOk() {
        String content = "fruit, quantity\napple, 10\nbanana, 5";
        assertThrows(RuntimeException.class, () -> {
            fileWriter.write(content, "?:/Error");
        });
    }
}
