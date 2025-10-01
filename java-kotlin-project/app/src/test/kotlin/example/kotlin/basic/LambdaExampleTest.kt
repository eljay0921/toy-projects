package example.kotlin.basic

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class LambdaExampleTest {

    @Test
    fun testBasicLambdas() {
        // 기본 람다 표현식 테스트
        val add: (Int, Int) -> Int = { a, b -> a + b }
        val multiply = { a: Int, b: Int -> a * b }

        assertEquals(10, add(7, 3), "7 + 3 should equal 10")
        assertEquals(21, multiply(7, 3), "7 * 3 should equal 21")
    }

    @Test
    fun testComplexLambda() {
        // 복잡한 람다 표현식 테스트 (마지막 표현식이 반환값)
        val complexCal = { a: Int, b: Int ->
            var result = a + b
            result *= 2
            result // 마지막 표현식이 반환값
        }

        assertEquals(20, complexCal(6, 4), "(6 + 4) * 2 should equal 20")
        assertEquals(12, complexCal(2, 4), "(2 + 4) * 2 should equal 12")
    }

    @Test
    fun testUnitLambda() {
        // 매개변수가 없는 람다 테스트
        var executed = false
        val task: () -> Unit = { executed = true }

        task()
        assertTrue(executed, "Unit lambda should have been executed")
    }

    @Test
    fun testItParameter() {
        // 매개변수가 하나인 람다 테스트 (it 사용)
        var capturedMessage: String? = null
        val printer: (String) -> Unit = { capturedMessage = it }

        printer("Hello Lambda!")
        assertEquals("Hello Lambda!", capturedMessage, "Lambda should capture message using 'it'")
    }

    @Test
    fun testNamedParameter() {
        // 명시적으로 이름을 지정한 람다 테스트
        var capturedMessage: String? = null
        val printerWithName: (String) -> Unit = { msg -> capturedMessage = msg }

        printerWithName("Hello Named Lambda!")
        assertEquals("Hello Named Lambda!", capturedMessage, "Lambda should capture named parameter")
    }

    @Test
    fun testVariousFunctionTypes() {
        // 다양한 함수 타입 테스트
        val isEven: (Int) -> Boolean = { it % 2 == 0 }
        val square: (Int) -> Int = { it * it }

        assertTrue(isEven(4), "4 should be even")
        assertFalse(isEven(5), "5 should be odd")
        assertEquals(36, square(6), "6 squared should equal 36")
        assertEquals(25, square(5), "5 squared should equal 25")
    }

    @Test
    fun testHigherOrderFunctionUsage() {
        // 고차함수와 람다 조합 테스트
        val numbers = listOf(1, 2, 3, 4, 5)

        val evenNumbers = numbers.filter { it % 2 == 0 }
        val squaredNumbers = numbers.map { it * it }
        val sum = numbers.reduce { acc, num -> acc + num }

        assertEquals(listOf(2, 4), evenNumbers, "Should filter even numbers")
        assertEquals(listOf(1, 4, 9, 16, 25), squaredNumbers, "Should square all numbers")
        assertEquals(15, sum, "Sum should equal 15")
    }
}