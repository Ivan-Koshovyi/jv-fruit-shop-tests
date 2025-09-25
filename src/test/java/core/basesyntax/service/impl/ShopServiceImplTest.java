package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceOperation;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseOperation;
import core.basesyntax.strategy.impl.ReturnOperation;
import core.basesyntax.strategy.impl.SupplyOperation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private static ShopService shopService;

    @BeforeAll
    static void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @Test
    void shopService_Ok() {
        List<FruitTransaction> transactions = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 10),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 50),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 30),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 30)
        );
        Map<String, Integer> process = shopService.process(transactions);

        Integer apple = process.get("apple");
        Integer banana = process.get("banana");
        assertEquals(110, apple);
        assertEquals(10, banana);
    }

    @Test
    void balanceIsInvalid_NotOk() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", -20)
        );
        assertThrows(IllegalArgumentException.class, () -> {
            shopService.process(transactions);
        });
    }

    @Test
    void supplyIsInvalid_NotOk() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", -20)
        );
        assertThrows(IllegalArgumentException.class, () -> {
            shopService.process(transactions);
        });
    }

    @Test
    void purchaseIsInvalid_NotOk() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 20)
        );
        assertThrows(IllegalArgumentException.class, () -> {
            shopService.process(transactions);
        });
    }

    @Test
    void returnIsInvalid_NotOk() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", -20)
        );
        assertThrows(IllegalArgumentException.class, () -> {
            shopService.process(transactions);
        });
    }

    @Test
    void unknownOperation_NotOk() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(null, "banana", -20)
        );
        assertThrows(IllegalArgumentException.class, () -> {
            shopService.process(transactions);
        });
    }
}
