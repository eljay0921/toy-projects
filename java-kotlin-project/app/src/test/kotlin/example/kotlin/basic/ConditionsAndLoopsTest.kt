package example.kotlin.basic

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class ConditionsAndLoopsTest {

    private val conditionsAndLoops = ConditionsAndLoops()

    @Test
    fun getGradeTest() {

        // grade

        val gradeA = conditionsAndLoops.getGrade(90)
        assertEquals("A", gradeA)

        val gradeB = conditionsAndLoops.getGrade(89)
        assertEquals("B", gradeB)

        val gradeC = conditionsAndLoops.getGrade(75)
        assertEquals("C", gradeC)

        val gradeF = conditionsAndLoops.getGrade(69)
        assertEquals("F", gradeF)

        // grade2

        val gradeA2 = conditionsAndLoops.getGrade2(99)
        assertEquals("A", gradeA2)

        val gradeB2 = conditionsAndLoops.getGrade2(80)
        assertEquals("B", gradeB2)

        val gradeC2 = conditionsAndLoops.getGrade2(70)
        assertEquals("C", gradeC2)

        val gradeF2 = conditionsAndLoops.getGrade2(0)
        assertEquals("F", gradeF2)
    }

    @Test
    fun getDayTypeTest() {
        val dayTypeWeekend = conditionsAndLoops.getDayType("SUNDAY")
        assertEquals("WeekEnd", dayTypeWeekend)

        val dayTypeWeekday = conditionsAndLoops.getDayType("TUESDAY")
        assertEquals("WeekDay", dayTypeWeekday)

        val dayTypeUnknown = conditionsAndLoops.getDayType("Holiday")
        assertEquals("unknown", dayTypeUnknown)
    }

    @Test
    fun getTemperatureDescriptionTest() {
        // Freezing 테스트
        val freezing1 = conditionsAndLoops.getTemperatureDescription(-5)
        assertEquals("Freezing", freezing1)
        val freezing2 = conditionsAndLoops.getTemperatureDescription(-1)
        assertEquals("Freezing", freezing2)

        // Cold 테스트
        val cold1 = conditionsAndLoops.getTemperatureDescription(0)
        assertEquals("Cold", cold1)
        val cold2 = conditionsAndLoops.getTemperatureDescription(5)
        assertEquals("Cold", cold2)

        // Cool 테스트
        val cool1 = conditionsAndLoops.getTemperatureDescription(10)
        assertEquals("Cool", cool1)
        val cool2 = conditionsAndLoops.getTemperatureDescription(15)
        assertEquals("Cool", cool2)

        // Warm 테스트
        val warm1 = conditionsAndLoops.getTemperatureDescription(20)
        assertEquals("Warm", warm1)
        val warm2 = conditionsAndLoops.getTemperatureDescription(25)
        assertEquals("Warm", warm2)

        // Hot 테스트
        val hot1 = conditionsAndLoops.getTemperatureDescription(30)
        assertEquals("Hot", hot1)
        val hot2 = conditionsAndLoops.getTemperatureDescription(40)
        assertEquals("Hot", hot2)
    }

    @Test
    fun processValueTest() {
        // String 타입 테스트
        val stringResult = conditionsAndLoops.processValue("Hello")
        assertEquals("Text: Hello", stringResult)

        // Int 타입 테스트
        val intResult = conditionsAndLoops.processValue(42)
        assertEquals("Number: 42", intResult)

        // List 타입 테스트
        val listResult = conditionsAndLoops.processValue(listOf(1, 2, 3))
        assertEquals("List with 3 items", listResult)

        val emptyListResult = conditionsAndLoops.processValue(emptyList<String>())
        assertEquals("List with 0 items", emptyListResult)

        // 다른 타입 테스트
        val doubleResult = conditionsAndLoops.processValue(3.14)
        assertEquals("Unknown type", doubleResult)

        val booleanResult = conditionsAndLoops.processValue(true)
        assertEquals("Unknown type", booleanResult)
    }
}