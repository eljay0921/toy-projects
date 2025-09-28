package example.java.dataclass;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    @Test
    void testDefaultConstructor() {
        Person person = new Person();
        assertNull(person.getName());
        assertEquals(0, person.getAge());
        assertNull(person.getEmail());
    }

    @Test
    void testParameterizedConstructor() {
        Person person = new Person("John", 25, "john@example.com");
        assertEquals("John", person.getName());
        assertEquals(25, person.getAge());
        assertEquals("john@example.com", person.getEmail());
    }

    @Test
    void testSettersAndGetters() {
        Person person = new Person();
        person.setName("Jane");
        person.setAge(30);
        person.setEmail("jane@example.com");

        assertEquals("Jane", person.getName());
        assertEquals(30, person.getAge());
        assertEquals("jane@example.com", person.getEmail());
    }

    @Test
    void testEquals() {
        Person person1 = new Person("John", 25, "john@example.com");
        Person person2 = new Person("John", 25, "john@example.com");
        Person person3 = new Person("Jane", 25, "john@example.com");

        assertEquals(person1, person2);
        assertNotEquals(person1, person3);
        assertNotEquals(person1, null);
        assertEquals(person1, person1);
    }

    @Test
    void testHashCode() {
        Person person1 = new Person("John", 25, "john@example.com");
        Person person2 = new Person("John", 25, "john@example.com");

        assertEquals(person1.hashCode(), person2.hashCode());
    }

    @Test
    void testToString() {
        Person person = new Person("John", 25, "john@example.com");
        String expected = "Person{name='John', age=25, email='john@example.com'}";
        assertEquals(expected, person.toString());
    }
}