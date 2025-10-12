package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private SupplyOperation supplyOperation;
    private Map<String, Integer> storage;

    @BeforeEach
    void setUp() {
        supplyOperation = new SupplyOperation();
        storage = new HashMap<>();
    }

    @Test
    void handle_shouldIncreaseExistingStock_whenSupplyOperation() {
        storage.put("Apple", 10);
        FruitTransaction transaction
                = new FruitTransaction(FruitTransaction.Operation.SUPPLY, "Apple", 10);
        supplyOperation.handle(transaction,storage);
        assertEquals(20, storage.get("Apple"));
    }

    @Test
    void handle_shouldAddNewFruitToStock_whenSupplyOperation() {
        FruitTransaction transaction
                = new FruitTransaction(FruitTransaction.Operation.SUPPLY, "Apple", 10);
        supplyOperation.handle(transaction,storage);
        assertEquals(10, storage.get("Apple"));
    }

    @Test
    void handle_shouldThrowException_whenSupplyQuantityIsNegative() {
        FruitTransaction transaction
                = new FruitTransaction(FruitTransaction.Operation.SUPPLY, "Apple", -10);

        assertThrows(IllegalArgumentException.class,
                () -> supplyOperation.handle(transaction,storage));
    }
}
