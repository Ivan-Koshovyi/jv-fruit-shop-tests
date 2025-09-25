package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ReportGenerator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ReportGeneratorImplTest {
    private static ReportGenerator reportGenerator;

    @BeforeAll
    static void setUp() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    void reportGenerator_Ok() {

        Map fruitMap = new HashMap<>() {{
            put("banana", 40);
            put("apple", 30);
        }
        };

        String report = reportGenerator.getReport(fruitMap);

        assertTrue(report.contains("fruit, quantity"));

        assertTrue(report.contains("banana,40"));
        assertTrue(report.contains("apple,30"));
    }
}