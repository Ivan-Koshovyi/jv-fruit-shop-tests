package core.basesyntax.service.impl;

import core.basesyntax.service.ReadFile;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class ReadFileImplTest {
    private static ReadFile readFile;
    private static List <String> LIST_RESALT_READING_IS_OK;
    private static List <String> LIST_RESALT_READING_IS_NOT_OK;
    private static String linkFile;

    @BeforeAll
    static void setUp() {
        readFile = new ReadFileImpl();
        LIST_RESALT_READING_IS_OK = List.of("b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        LIST_RESALT_READING_IS_NOT_OK = List.of("b,banana,20",
                "b,apple,100");
        linkFile = "src/main/resources/reportToRead.csv";
    }

    @Test
    void readFile_OK() {
        String string = linkFile;
        List<String> actual = readFile.read(string);
        assertEquals(LIST_RESALT_READING_IS_OK, actual);
    }

    @Test
    void readFile_NotOK() {
        String string = linkFile;
        List<String> actual = readFile.read(string);
        assertNotEquals(LIST_RESALT_READING_IS_NOT_OK, actual);
    }

    @Test
    void fileNotFound_NotOk() {
        assertThrows(RuntimeException.class, () -> {
           readFile.read("src/main/resources/NonExistentFile.csv");
        });
    }
}