package example.kotlin.dataclass

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class PersonTest {

    @Test
    fun testDataClassCreation() {
        val person = Person("John", 25, "john@example.com")
        assertEquals("John", person.name)
        assertEquals(25, person.age)
        assertEquals("john@example.com", person.email)
    }

    @Test
    fun testDataClassEquals() {
        val person1 = Person("John", 25, "john@example.com")
        val person2 = Person("John", 25, "john@example.com")
        val person3 = Person("Jane", 25, "john@example.com")

        assertEquals(person1, person2)
        assertNotEquals(person1, person3)
    }

    @Test
    fun testDataClassHashCode() {
        val person1 = Person("John", 25, "john@example.com")
        val person2 = Person("John", 25, "john@example.com")

        assertEquals(person1.hashCode(), person2.hashCode())
    }

    @Test
    fun testDataClassToString() {
        val person = Person("John", 25, "john@example.com")
        val expected = "Person(name=John, age=25, email=john@example.com)"
        assertEquals(expected, person.toString())
    }

    @Test
    fun testDataClassCopy() {
        val originalPerson = Person("John", 25, "john@example.com")
        val copiedPerson = originalPerson.copy()
        val modifiedPerson = originalPerson.copy(age = 30)

        assertEquals(originalPerson, copiedPerson)
        assertEquals("John", modifiedPerson.name)
        assertEquals(30, modifiedPerson.age)
        assertEquals("john@example.com", modifiedPerson.email)
        assertNotEquals(originalPerson, modifiedPerson)
    }

    @Test
    fun testDataClassDestructuring() {
        val person = Person("John", 25, "john@example.com")
        val (name, age, email) = person

        assertEquals("John", name)
        assertEquals(25, age)
        assertEquals("john@example.com", email)
    }
}