package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private PurchaseOperation purchaseOperation;
    private Map<String, Integer> storage;

    @BeforeEach
    void setUp() {
        purchaseOperation = new PurchaseOperation();
        storage = new HashMap<>();
    }

    @Test
    void handle_shouldBuyExactStock_StockBecomesZero() {
        storage.put("Apple", 20);
        FruitTransaction transaction
                = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "Apple", 20);
        purchaseOperation.handle(transaction, storage);
        assertEquals(0, storage.get("Apple"));
    }

    @Test
    void handle_shouldThrowException_whenFruitNotInStock() {
        FruitTransaction transaction
                = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "Banana", 5);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> purchaseOperation.handle(transaction, storage));
    }

    @Test
    void handle_shouldNotChangeStock_whenQuantityIsZero() {
        storage.put("Apple", 10);
        int actual = storage.get("Apple");
        FruitTransaction transaction
                = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "Apple", 0);
        purchaseOperation.handle(transaction, storage);
        assertEquals(actual, storage.get("Apple"));
    }
}
