package example.java.basic;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LambdaExampleTest {

    @Test
    public void testBasicLambdas() {
        // 기본 람다 표현식 테스트
        Calculator add = (a, b) -> a + b;
        Calculator multiply = (a, b) -> a * b;

        assertEquals(10, add.calculate(7, 3), "7 + 3 should equal 10");
        assertEquals(21, multiply.calculate(7, 3), "7 * 3 should equal 21");
    }

    @Test
    public void testComplexLambda() {
        // 복잡한 람다 표현식 테스트
        Calculator complexCal = (a, b) -> {
            int result = a + b;
            result *= 2;
            return result;
        };

        assertEquals(20, complexCal.calculate(6, 4), "(6 + 4) * 2 should equal 20");
        assertEquals(14, complexCal.calculate(3, 4), "(3 + 4) * 2 should equal 14");
    }

    @Test
    public void testRunnableLambda() {
        // 매개변수가 없는 람다 테스트
        final boolean[] executed = {false};
        Runnable task = () -> executed[0] = true;

        task.run();
        assertTrue(executed[0], "Runnable task should have been executed");
    }

    @Test
    public void testPrinterLambda() {
        // 매개변수가 하나인 람다 테스트
        final String[] capturedMessage = {null};
        Printer printer = msg -> capturedMessage[0] = msg;

        printer.print("Hello Lambda!");
        assertEquals("Hello Lambda!", capturedMessage[0], "Printer should capture the message");
    }

    @Test
    public void testMethodReference() {
        // 메서드 참조 테스트
        Calculator integerSum = Integer::sum;
        assertEquals(15, integerSum.calculate(7, 8), "Integer.sum(7, 8) should equal 15");
    }
}