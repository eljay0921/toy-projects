package example.kotlin.basic

import example.kotlin.dataclass.Person
import example.kotlin.dataclass.User
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows

class NullSafetyTest {

    private val nullSafety = NullSafety()

    @Test
    fun getUpperCaseNameTest() {
        val person = Person("jake", 33, "jake@gmail.com")
        val upperName = nullSafety.getUpperCaseName(person);
        assertEquals("JAKE", upperName);

        val upperNull = nullSafety.getUpperCaseName(null)
        assertEquals("UNKNOWN", upperNull);
    }

    @Test
    fun getDisplayNameTest() {
        val user = User("jane", "jane@gmail.com", 25, false)
        val userName = nullSafety.getUserDisplayName(user)
        assertEquals("jane", userName)

        val userNoName = nullSafety.getUserDisplayName(null)
        assertEquals("Anonymous", userNoName)
    }

    @Test
    fun getUserEmailDomainTest() {
        val user = User("jin", "jin@gmail.com")
        val userEmailDomain = nullSafety.getUserEmailDomain(user)
        assertEquals("gmail.com", userEmailDomain)

        val userNoMail = User("jin")
        val userEmailNull = nullSafety.getUserEmailDomain(userNoMail)
        assertEquals("unknown", userEmailNull)

        val userNull = nullSafety.getUserEmailDomain(null)
        assertEquals("unknown", userNull)
    }

    @Test
    fun processUserTest() {
        val user = User("jane", "jane@gmail.com", 25, false)

        // processUser는 void 메서드이므로 예외가 발생하지 않는지 확인
        assertDoesNotThrow {
            nullSafety.processUser(user)
        }

        // null 사용자도 예외가 발생하지 않는지 확인
        assertDoesNotThrow {
            nullSafety.processUser(null)
        }
    }

    @Test
    fun riskyFunctionTest() {
        // 정상적인 문자열 전달
        assertDoesNotThrow {
            nullSafety.riskyFunction("test")
        }

        // null 전달 시 NullPointerException 발생 확인
        assertThrows<NullPointerException> {
            nullSafety.riskyFunction(null)
        }
    }

    @Test
    fun smartCastExampleTest() {
        // 정상적인 문자열 전달
        assertDoesNotThrow {
            nullSafety.smartCastExample("test")
        }

        // null 전달해도 예외 발생하지 않음 (if문에서 체크하므로)
        assertDoesNotThrow {
            nullSafety.smartCastExample(null)
        }
    }
}