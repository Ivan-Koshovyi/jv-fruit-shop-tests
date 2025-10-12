package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private BalanceOperation balanceOperation;
    private Map<String, Integer> storage;

    @BeforeEach
    void setUp() {
        balanceOperation = new BalanceOperation();
        storage = new HashMap<>();
    }

    @Test
    void handle_shouldUpdateStorage_whenQuantityIsPositive() {
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE, "Apple", 10);
        balanceOperation.handle(transaction, storage);
        assertEquals(10, storage.get("Apple"));
    }

    @Test
    void handle_shouldThrowException_whenQuantityIsNegative() {
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE, "Banana", -5);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> balanceOperation.handle(transaction, storage)
        );

        assertEquals("Balance quantity cannot be negative: -5", exception.getMessage());
    }

    @Test
    void handle_shouldReplaceExistingQuantity() {
        storage.put("Orange", 5);
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE, "Orange", 15);
        balanceOperation.handle(transaction, storage);
        assertEquals(15, storage.get("Orange"));
    }
}
