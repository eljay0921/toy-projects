package example.java.dataclass;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testDefaultConstructor() {
        User user = new User();
        assertNull(user.getUsername());
        assertNull(user.getEmail());
        assertEquals(0, user.getAge());
        assertTrue(user.isActive());
    }

    @Test
    void testUsernameConstructor() {
        User user = new User("testuser");
        assertEquals("testuser", user.getUsername());
        assertNull(user.getEmail());
        assertEquals(0, user.getAge());
        assertTrue(user.isActive());
    }

    @Test
    void testUsernameEmailConstructor() {
        User user = new User("testuser", "test@example.com");
        assertEquals("testuser", user.getUsername());
        assertEquals("test@example.com", user.getEmail());
        assertEquals(0, user.getAge());
        assertTrue(user.isActive());
    }

    @Test
    void testFullConstructor() {
        User user = new User("testuser", "test@example.com", 25, false);
        assertEquals("testuser", user.getUsername());
        assertEquals("test@example.com", user.getEmail());
        assertEquals(25, user.getAge());
        assertFalse(user.isActive());
    }

    @Test
    void testSettersAndGetters() {
        User user = new User();
        user.setUsername("newuser");
        user.setEmail("new@example.com");
        user.setAge(30);
        user.setActive(false);

        assertEquals("newuser", user.getUsername());
        assertEquals("new@example.com", user.getEmail());
        assertEquals(30, user.getAge());
        assertFalse(user.isActive());
    }

    @Test
    void testActiveStatusToggle() {
        User user = new User("testuser");
        assertTrue(user.isActive());

        user.setActive(false);
        assertFalse(user.isActive());

        user.setActive(true);
        assertTrue(user.isActive());
    }
}