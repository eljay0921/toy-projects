package example.java.basic;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HigherOrderFunctionsTest {

    @Test
    public void testOperation() {
        HigherOrderFunctions hof = new HigherOrderFunctions();

        // operation 메서드 테스트
        int addResult = hof.operation(5, 3, (a, b) -> a + b);
        int multiplyResult = hof.operation(5, 3, (a, b) -> a * b);

        assertEquals(8, addResult, "5 + 3 should equal 8");
        assertEquals(15, multiplyResult, "5 * 3 should equal 15");
    }

    @Test
    public void testGetOperation() {
        HigherOrderFunctions hof = new HigherOrderFunctions();

        // getOperation 메서드 테스트
        Calculator addCalc = hof.getOperation("add");
        Calculator multiplyCalc = hof.getOperation("multiply");
        Calculator defaultCalc = hof.getOperation("invalid");

        assertEquals(12, addCalc.calculate(10, 2), "add operation: 10 + 2 should equal 12");
        assertEquals(20, multiplyCalc.calculate(10, 2), "multiply operation: 10 * 2 should equal 20");
        assertEquals(0, defaultCalc.calculate(10, 2), "invalid operation should return 0");
    }

    @Test
    public void testVariousOperations() {
        HigherOrderFunctions hof = new HigherOrderFunctions();

        // 다양한 람다 표현식 테스트
        int subtractResult = hof.operation(10, 3, (a, b) -> a - b);
        int divideResult = hof.operation(15, 3, (a, b) -> a / b);
        int maxResult = hof.operation(7, 9, Math::max);

        assertEquals(7, subtractResult, "10 - 3 should equal 7");
        assertEquals(5, divideResult, "15 / 3 should equal 5");
        assertEquals(9, maxResult, "max(7, 9) should equal 9");
    }
}