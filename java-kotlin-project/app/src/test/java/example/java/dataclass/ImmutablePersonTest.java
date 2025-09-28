package example.java.dataclass;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ImmutablePersonTest {

    @Test
    void testConstructor() {
        List<String> hobbies = Arrays.asList("reading", "swimming");
        ImmutablePerson person = new ImmutablePerson("John", 25, hobbies);

        assertEquals("John", person.getName());
        assertEquals(25, person.getAge());
        assertEquals(hobbies, person.getHobbies());
    }

    @Test
    void testImmutabilityOfHobbies() {
        List<String> originalHobbies = Arrays.asList("reading", "swimming");
        ImmutablePerson person = new ImmutablePerson("John", 25, originalHobbies);

        List<String> retrievedHobbies = person.getHobbies();
        retrievedHobbies.add("cycling");

        assertEquals(2, person.getHobbies().size());
        assertFalse(person.getHobbies().contains("cycling"));
    }

    @Test
    void testDefensiveCopyInConstructor() {
        List<String> hobbies = new ArrayList<>(Arrays.asList("reading", "swimming"));
        ImmutablePerson person = new ImmutablePerson("John", 25, hobbies);

        hobbies.add("cycling");

        assertEquals(2, person.getHobbies().size());
        assertFalse(person.getHobbies().contains("cycling"));
    }

    @Test
    void testWithAge() {
        List<String> hobbies = Arrays.asList("reading", "swimming");
        ImmutablePerson originalPerson = new ImmutablePerson("John", 25, hobbies);
        ImmutablePerson newPerson = originalPerson.withAge(30);

        assertEquals("John", newPerson.getName());
        assertEquals(30, newPerson.getAge());
        assertEquals(hobbies, newPerson.getHobbies());

        assertEquals(25, originalPerson.getAge());
        assertNotSame(originalPerson, newPerson);
    }

    @Test
    void testWithAgePreservesHobbies() {
        List<String> hobbies = Arrays.asList("reading", "swimming");
        ImmutablePerson originalPerson = new ImmutablePerson("John", 25, hobbies);
        ImmutablePerson newPerson = originalPerson.withAge(30);

        assertEquals(originalPerson.getHobbies(), newPerson.getHobbies());
        assertNotSame(originalPerson.getHobbies(), newPerson.getHobbies());
    }

    @Test
    void testEmptyHobbiesList() {
        List<String> emptyHobbies = Arrays.asList();
        ImmutablePerson person = new ImmutablePerson("Jane", 30, emptyHobbies);

        assertEquals("Jane", person.getName());
        assertEquals(30, person.getAge());
        assertTrue(person.getHobbies().isEmpty());
    }
}