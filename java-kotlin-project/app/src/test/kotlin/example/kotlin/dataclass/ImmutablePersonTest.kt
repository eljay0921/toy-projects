package example.kotlin.dataclass

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotSame

class ImmutablePersonTest {

    @Test
    fun testDataClassCreation() {
        val hobbies = listOf("reading", "swimming")
        val person = ImmutablePerson("John", 25, hobbies)

        assertEquals("John", person.name)
        assertEquals(25, person.age)
        assertEquals(hobbies, person.hobbies)
    }

    @Test
    fun testDataClassEquals() {
        val hobbies = listOf("reading", "swimming")
        val person1 = ImmutablePerson("John", 25, hobbies)
        val person2 = ImmutablePerson("John", 25, hobbies)
        val person3 = ImmutablePerson("Jane", 25, hobbies)

        assertEquals(person1, person2)
        assertNotEquals(person1, person3)
    }

    @Test
    fun testDataClassCopy() {
        val hobbies = listOf("reading", "swimming")
        val originalPerson = ImmutablePerson("John", 25, hobbies)
        val copiedPerson = originalPerson.copy()
        val modifiedPerson = originalPerson.copy(age = 30)

        assertEquals(originalPerson, copiedPerson)
        assertEquals("John", modifiedPerson.name)
        assertEquals(30, modifiedPerson.age)
        assertEquals(hobbies, modifiedPerson.hobbies)
        assertNotEquals(originalPerson, modifiedPerson)
    }

    @Test
    fun testWithAge() {
        val hobbies = listOf("reading", "swimming")
        val originalPerson = ImmutablePerson("John", 25, hobbies)
        val newPerson = originalPerson.withAge(30)

        assertEquals("John", newPerson.name)
        assertEquals(30, newPerson.age)
        assertEquals(hobbies, newPerson.hobbies)

        assertEquals(25, originalPerson.age)
        assertNotSame(originalPerson, newPerson)
    }

    @Test
    fun testToString() {
        val hobbies = listOf("reading", "swimming")
        val person = ImmutablePerson("John", 25, hobbies)
        val expected = "ImmutablePerson(name=John, age=25, hobbies=[reading, swimming])"
        assertEquals(expected, person.toString())
    }

    @Test
    fun testDestructuring() {
        val hobbies = listOf("reading", "swimming")
        val person = ImmutablePerson("John", 25, hobbies)
        val (name, age, personHobbies) = person

        assertEquals("John", name)
        assertEquals(25, age)
        assertEquals(hobbies, personHobbies)
    }

    @Test
    fun testEmptyHobbiesList() {
        val emptyHobbies = emptyList<String>()
        val person = ImmutablePerson("Jane", 30, emptyHobbies)

        assertEquals("Jane", person.name)
        assertEquals(30, person.age)
        assertEquals(emptyHobbies, person.hobbies)
    }

    @Test
    fun testHobbiesListImmutability() {
        val hobbies = listOf("reading", "swimming")
        val person = ImmutablePerson("John", 25, hobbies)

        assertEquals(2, person.hobbies.size)
        assertEquals("reading", person.hobbies[0])
        assertEquals("swimming", person.hobbies[1])
    }
}