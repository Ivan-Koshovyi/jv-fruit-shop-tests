package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.service.ReadFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReadFileImplTest {
    private static ReadFile readFile;
    private static List<String> LIST_RESULT_READING_IS_OK;
    private static List<String> LIST_RESULT_READING_IS_NOT_OK;
    private static String linkFile;

    @BeforeAll
    static void setUp() {
        readFile = new ReadFileImpl();
        LIST_RESULT_READING_IS_OK = List.of("b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        LIST_RESULT_READING_IS_NOT_OK = List.of("b,banana,20",
                "b,apple,100");
        linkFile = "src/main/resources/reportToRead.csv";
    }

    @Test
    void readFile_OK() {
        Path tempFile = null;
        try {
            tempFile = Files.createTempFile("testFile", ".txt");
            Files.writeString(tempFile, "type,fruit,quantity\n"
                    + "b,banana,20\n"
                    + "b,apple,100\n"
                    + "s,banana,100\n"
                    + "p,banana,13\n"
                    + "r,apple,10\n"
                    + "p,apple,20\n"
                    + "p,banana,5\n"
                    + "s,banana,50");
            List<String> actual = readFile.read(String.valueOf(tempFile));
            assertEquals(LIST_RESULT_READING_IS_OK, actual);
            Files.deleteIfExists(tempFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void readFile_NotOK() {
        String string = linkFile;
        List<String> actual = readFile.read(string);
        assertNotEquals(LIST_RESULT_READING_IS_NOT_OK, actual);
    }

    @Test
    void fileNotFound_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            readFile.read("$#NonExistentFile.csv");
        });
    }

    @Test
    void fileIsNull_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            readFile.read(null);
        });
    }
}
