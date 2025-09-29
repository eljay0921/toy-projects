package example.kotlin.basic

class ConditionsAndLoops {

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
}