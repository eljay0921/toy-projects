package example.kotlin.basic

class ConditionsAndLoops {

    // 조건문 ==============================================================================

    // if는 표현식 (expression)
    fun getGrade(score: Int): String {
        return if (score >= 90) {
            "A"
        } else if (score >= 80) {
            "B"
        } else if (score >= 70) {
            "C"
        } else {
            "F"
        }
    }

    // 더 간결한 형태
    fun getGrade2(score: Int) = if (score >= 90) "A"
                                else if (score >= 80) "B"
                                else if (score >= 70) "C"
                                else "F"

    // when 표현식 (switch 대체)
    fun getDayType(day: String) = when (day) {
        "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY" -> "WeekDay"
        "SATURDAY", "SUNDAY" -> "WeekEnd"
        else -> "unknown"
    }

    // when with 조건식
    fun getTemperatureDescription(temp: Int) = when {
        temp < 0 -> "Freezing"
        temp < 10 -> "Cold"
        temp < 20 -> "Cool"
        temp < 30 -> "Warm"
        else -> "Hot"
    }

    // when with 타입 체크
    fun processValue(value: Any) = when (value) {
        is String -> "Text: $value"
        is Int -> "Number: $value"
        is List<*> -> "List with ${value.size} items"
        else -> "Unknown type"
    }

    // 반복문 ==============================================================================

    fun normalLoop() {
        for (i in 0..9) {   // 0 ~ 9 (포함)
            println(i)
        }
    }

    fun untilLoop() {
        for (i in 0 until 10) { // 0 ~ 9 (10 미포함)
            println(i)
        }
    }

    fun downToLoop() {
        for (i in 9 downTo 0) { // 9 ~ 0 (역순 / 0 포함)
            println(i)
        }
    }

    fun stepLoop() {
        for (i in 0..20 step 2) { // 0, 2, 4, 6, ... 20
            println(i)
        }
    }

    fun collectionLoop() {
        val friends = listOf("jin", "jake", "jane")
        for (name in friends) {
            println(name)
        }
    }

    fun collectionLoopFunctional() {
        val friends = listOf("jin", "jake", "jane")
        friends.filter { it.length > 3 }
            .map { it.uppercase() }
            .forEach { println(it) }
    }

    fun repeatLoop() {
        repeat(5) { idx -> println("Iteration: $idx") }
    }

    fun withIndexLoop() {
        val friends = listOf("jin", "jake", "jane")
        for ((idx, name) in friends.withIndex()) {
            println("$idx: $name")
        }
    }

    fun whileLoop() {   // java와 동일
        var count = 0
        while (count < 5) {
            println(count)
            count++
        }
    }
}