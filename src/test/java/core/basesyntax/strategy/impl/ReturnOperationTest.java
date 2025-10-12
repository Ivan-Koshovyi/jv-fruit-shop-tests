package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private ReturnOperation returnOperation;
    private Map<String, Integer> storage;

    @BeforeEach
    void setUp() {
        returnOperation = new ReturnOperation();
        storage = new HashMap<>();
    }

    @Test
    void handle_shouldIncreaseExistingStock_whenReturnOperation() {
        storage.put("Apple", 10);
        FruitTransaction transaction
                = new FruitTransaction(FruitTransaction.Operation.RETURN, "Apple", 10);
        returnOperation.handle(transaction, storage);
        assertEquals(20, storage.get("Apple"));
    }

    @Test
    void handle_shouldAddNewFruitToStock_whenReturnOperation() {
        FruitTransaction transaction
                = new FruitTransaction(FruitTransaction.Operation.RETURN, "Apple", 10);
        returnOperation.handle(transaction, storage);
        assertEquals(10, storage.get("Apple"));
    }

    @Test
    void handle_shouldThrowException_whenReturnQuantityIsNegative() {
        FruitTransaction transaction
                = new FruitTransaction(FruitTransaction.Operation.RETURN, "Apple", -10);
        assertThrows(IllegalArgumentException.class,
                () -> returnOperation.handle(transaction, storage)
        );
    }
}
