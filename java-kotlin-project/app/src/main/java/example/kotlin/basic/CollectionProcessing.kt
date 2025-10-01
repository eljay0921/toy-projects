package example.kotlin.basic

class CollectionProcessing {

    fun sample() {

        val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

        // filter 짝수만
        val evenNumbers = numbers.filter { it % 2 == 0 }

        // map 각 요소를 제곱
        val squaredNumbers = numbers.map { it * it }

        // filter + map
        val evenSquaredNumbers = numbers
            .filter{ it % 2 == 0 }
            .map { it * it }

        // reduce 합계 구하기
        val sum = numbers.reduce { acc, it -> acc + it }
        val sum2 = numbers.sum()

        // forEach 각 요소 출력
        numbers.forEach { println(it) }

        // 복잡한 예제 : 짝수들의 제곱의 합
        val result = numbers.filter { it % 2 == 0 }
            .map { it * it }
            .sum()
        // 위 내용에 대한 intellij 제안
        // val result = numbers.filter { it % 2 == 0 }.sumOf { it * it }
        println(result)

        // 그 외
        val hasEven = numbers.any { it % 2 == 0 } // true
        val allPositive = numbers.all { it > 0 } // true
        val noNegative = numbers.none { it < 0 } // true (none: 조건을 만족하는 원소가 없는가?)
    }
}