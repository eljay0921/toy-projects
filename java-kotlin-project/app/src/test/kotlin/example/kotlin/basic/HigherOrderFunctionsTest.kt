package example.kotlin.basic

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class HigherOrderFunctionsTest {

    @Test
    fun testOperation() {
        val hof = HigherOrderFunctions()

        // operation 메서드 테스트
        val addResult = hof.operation(5, 3) { a, b -> a + b }
        val multiplyResult = hof.operation2(5, 3) { a, b -> a * b }

        assertEquals(8, addResult, "5 + 3 should equal 8")
        assertEquals(15, multiplyResult, "5 * 3 should equal 15")
    }

    @Test
    fun testGetOperation() {
        val hof = HigherOrderFunctions()

        // getOperation 메서드 테스트
        val addFunc = hof.getOperation("add")
        val multiplyFunc = hof.getOperation("multiply")
        val defaultFunc = hof.getOperation("invalid")

        assertEquals(12, addFunc(10, 2), "add operation: 10 + 2 should equal 12")
        assertEquals(20, multiplyFunc(10, 2), "multiply operation: 10 * 2 should equal 20")
        assertEquals(0, defaultFunc(10, 2), "invalid operation should return 0")
    }

    @Test
    fun testVariousOperations() {
        val hof = HigherOrderFunctions()

        // 다양한 람다 표현식 테스트
        val subtractResult = hof.operation(10, 3) { a, b -> a - b }
        val divideResult = hof.operation(15, 3) { a, b -> a / b }
        val maxResult = hof.operation(7, 9) { a, b -> maxOf(a, b) }

        assertEquals(7, subtractResult, "10 - 3 should equal 7")
        assertEquals(5, divideResult, "15 / 3 should equal 5")
        assertEquals(9, maxResult, "max(7, 9) should equal 9")
    }

    @Test
    fun testBothOperationMethods() {
        val hof = HigherOrderFunctions()

        // operation과 operation2 비교 테스트
        val result1 = hof.operation(4, 6) { a, b -> a + b }
        val result2 = hof.operation2(4, 6) { a, b -> a + b }

        assertEquals(result1, result2, "operation and operation2 should return same result")
        assertEquals(10, result1, "4 + 6 should equal 10")
    }
}