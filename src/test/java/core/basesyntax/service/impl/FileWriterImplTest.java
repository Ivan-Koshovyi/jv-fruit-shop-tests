package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileWriterImplTest {
    private static FileWriter fileWriter;
    private static String linkFile;

    @BeforeAll
    static void setUp() {
        fileWriter = new FileWriterImpl();
        linkFile = "src/main/resources/finalReport.csv";
    }

    @Test
    void fileWrite_Ok() {
        String content = "fruit, quantity\napple, 10\nbanana, 5";
        fileWriter.write(content, linkFile);
        String fileContent = null;
        try {
            fileContent = Files.readString(Path.of(linkFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(content, fileContent);
        assertTrue(fileContent.contains("apple"));
        assertTrue(fileContent.contains("banana"));
    }

    @Test
    void fileIsInvalid_NotOk() {
        String content = "fruit, quantity\napple, 10\nbanana, 5";
        assertThrows(RuntimeException.class, () -> {
            fileWriter.write(content, "?:/Error");
        });
    }
}