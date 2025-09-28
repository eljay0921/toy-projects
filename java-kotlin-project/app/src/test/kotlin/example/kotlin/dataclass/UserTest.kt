package example.kotlin.dataclass

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class UserTest {

    @Test
    fun testPrimaryConstructorWithDefaults() {
        val user = User("testuser")
        assertEquals("testuser", user.username)
        assertEquals("", user.email)
        assertEquals(0, user.age)
        assertTrue(user.isActive)
    }

    @Test
    fun testPrimaryConstructorAllParameters() {
        val user = User("testuser", "test@example.com", 25, false)
        assertEquals("testuser", user.username)
        assertEquals("test@example.com", user.email)
        assertEquals(25, user.age)
        assertEquals(false, user.isActive)
    }

    @Test
    fun testSecondaryConstructor() {
        val user = User("testuser")
        assertEquals("testuser", user.username)
        assertEquals("", user.email)
        assertEquals(0, user.age)
        assertTrue(user.isActive)
    }

    @Test
    fun testPartialParameterConstruction() {
        val user = User("testuser", "test@example.com")
        assertEquals("testuser", user.username)
        assertEquals("test@example.com", user.email)
        assertEquals(0, user.age)
        assertTrue(user.isActive)
    }

    @Test
    fun testDataClassEquals() {
        val user1 = User("testuser", "test@example.com", 25, true)
        val user2 = User("testuser", "test@example.com", 25, true)
        val user3 = User("testuser", "test@example.com", 30, true)

        assertEquals(user1, user2)
        assertNotEquals(user1, user3)
    }

    @Test
    fun testDataClassCopy() {
        val originalUser = User("testuser", "test@example.com", 25, true)
        val copiedUser = originalUser.copy()
        val modifiedUser = originalUser.copy(age = 30, isActive = false)

        assertEquals(originalUser, copiedUser)
        assertEquals("testuser", modifiedUser.username)
        assertEquals("test@example.com", modifiedUser.email)
        assertEquals(30, modifiedUser.age)
        assertEquals(false, modifiedUser.isActive)
    }

    @Test
    fun testDefaultValues() {
        val user1 = User("user1")
        val user2 = User("user2", "", 0, true)

        assertEquals(user1.email, user2.email)
        assertEquals(user1.age, user2.age)
        assertEquals(user1.isActive, user2.isActive)
    }

    @Test
    fun testToString() {
        val user = User("testuser", "test@example.com", 25, false)
        val expected = "User(username=testuser, email=test@example.com, age=25, isActive=false)"
        assertEquals(expected, user.toString())
    }
}