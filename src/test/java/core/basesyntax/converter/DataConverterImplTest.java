package core.basesyntax.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private static DataConverter dataConverter;

    @BeforeAll
    static void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertStringsToFruitTransactions_Ok() {
        List<String> input = Arrays.asList("b,apple,10", "s,banana,5");
        List<FruitTransaction> actual = dataConverter.convertToTransaction(input);
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 10),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 5)
        );
        assertEquals(expected, actual);
    }

    @Test
    void numberFormat_NotOk() {
        List<String> input = Arrays.asList("b,apple,ten");

        assertThrows(NumberFormatException.class, () -> {
            dataConverter.convertToTransaction(input);
        });
    }

    @Test
    void arrayLength_NotOk() {
        List<String> input = Arrays.asList("b,apple");

        assertThrows(RuntimeException.class, () -> {
            dataConverter.convertToTransaction(input);
        });
    }

    @Test
    void operationFormat_NotOk() {
        List<String> input = Arrays.asList("R,apple,10");

        assertThrows(IllegalArgumentException.class, () -> {
            dataConverter.convertToTransaction(input);
        });
    }

    @Test
    void arrayIsNull_NotOk() {
        List<String> input = Arrays.asList(null + "R,apple,10");
        assertThrows(IllegalArgumentException.class, () -> {
            dataConverter.convertToTransaction(input);
        });
    }
}
