package core.basesyntax.converter;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class DataConverterImplTest {
    private static DataConverter dataConverter;

    @BeforeAll
    static void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convert_Ok() {
        List<String> input = Arrays.asList("b,apple,10", "s,banana,5");

        List<FruitTransaction> transactions = dataConverter.convertToTransaction(input);

        assertEquals(2, transactions.size());

        FruitTransaction first = transactions.get(0);
        assertEquals(FruitTransaction.Operation.BALANCE, first.getOperation());
        assertEquals("apple", first.getFruit());
        assertEquals(10, first.getQuantity());

        FruitTransaction second = transactions.get(1);
        assertEquals(FruitTransaction.Operation.SUPPLY, second.getOperation());
        assertEquals("banana", second.getFruit());
        assertEquals(5, second.getQuantity());
    }

    @Test
    void invalidNumberFormat_NotOk() {
        List<String> input = Arrays.asList("b,apple,ten");

        assertThrows(NumberFormatException.class, () -> {
            dataConverter.convertToTransaction(input);
        });
    }

    @Test
    void invalidArrayLength_NotOk() {
        List<String> input = Arrays.asList("b,apple");

        assertThrows(RuntimeException.class, () -> {
            dataConverter.convertToTransaction(input);
        });
    }

    @Test
    void invalidOperationFormat_NotOk() {
        List<String> input = Arrays.asList("R,apple,10");

        assertThrows(IllegalArgumentException.class, () -> {
            dataConverter.convertToTransaction(input);
        });
    }
}