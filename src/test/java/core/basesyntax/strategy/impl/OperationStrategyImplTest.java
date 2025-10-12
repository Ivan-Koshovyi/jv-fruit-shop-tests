package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.strategy.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategyImpl strategy;
    private Map<String, Integer> storage;

    @BeforeEach
    void setUp() {
        storage = new HashMap<>();
        Map<Operation, OperationHandler> handlers = Map.of(
                Operation.BALANCE, new BalanceOperation(),
                Operation.RETURN, new ReturnOperation(),
                Operation.PURCHASE, new PurchaseOperation(),
                Operation.SUPPLY, new SupplyOperation()
        );
        strategy = new OperationStrategyImpl(handlers);
    }

    @Test
    void apply_shouldUpdateStorage_whenBalanceOperation() {
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE, "Apple", 10);
        strategy.apply(transaction, storage);
        assertEquals(10, storage.get("Apple"));
        transaction = new FruitTransaction(Operation.RETURN, "Apple", 10);
        strategy.apply(transaction, storage);
        assertEquals(20, storage.get("Apple"));
        transaction = new FruitTransaction(Operation.PURCHASE, "Apple", 10);
        strategy.apply(transaction, storage);
        assertEquals(10, storage.get("Apple"));
    }

    @Test
    void apply_shouldReplaceExistingQuantity_whenBalanceOperation() {
        storage.put("Apple", 5);
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE, "Apple", 20);
        strategy.apply(transaction, storage);
        assertEquals(20, storage.get("Apple"));
    }

    @Test
    void apply_shouldThrowException_whenBalanceQuantityNegative() {
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE, "Orange", -5);
        assertThrows(IllegalArgumentException.class, () -> strategy.apply(transaction, storage));
    }
}
