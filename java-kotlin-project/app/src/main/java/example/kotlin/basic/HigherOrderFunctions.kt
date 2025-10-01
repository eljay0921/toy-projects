package example.kotlin.basic

class HigherOrderFunctions {

    /**
     * 함수를 매개변수로 받는 함수
     */
    fun operation(a: Int, b: Int, calc: (Int, Int) -> Int): Int {
        return calc(a, b)
    }

    /**
     * 더 간결한 버전
     */
    fun operation2(a: Int, b: Int, calc: (Int, Int) -> Int) = calc(a, b)

    /**
     * 함수를 반환하는 함수
     */
    fun getOperation(type: String): (Int, Int) -> Int {
        return when (type) {
            "add" -> { a, b -> a + b }
            "multiply" -> { a, b -> a * b }
            else -> { _, _ -> 0 }
        }
    }

    fun sample() {

        // 람다를 인자로 전달
        val result1 = operation(2, 4) { a, b -> a + b }
        val result2 = operation2(2, 4) { a, b -> a * b }

        println(result1) // 6
        println(result2) // 8

        // 함수를 반환 받아 사용
        val adder = getOperation("add")
        println(adder(2, 4)) // 6

        val multiplier = getOperation("multiply")
        println(multiplier(2, 4)) // 8

        val error = getOperation("nothing")
        println(error(2, 4)) // 0
    }
}