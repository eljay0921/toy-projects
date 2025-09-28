package example.kotlin.dataclass

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class RectangleTest {

    @Test
    fun testConstructor() {
        val rectangle = Rectangle(5.0, 3.0)
        assertEquals(5.0, rectangle.width)
        assertEquals(3.0, rectangle.height)
    }

    @Test
    fun testAreaCalculation() {
        val rectangle = Rectangle(5.0, 3.0)
        assertEquals(15.0, rectangle.area)
    }

    @Test
    fun testSetValidWidth() {
        val rectangle = Rectangle(5.0, 3.0)
        rectangle.width = 10.0
        assertEquals(10.0, rectangle.width)
    }

    @Test
    fun testSetValidHeight() {
        val rectangle = Rectangle(5.0, 3.0)
        rectangle.height = 7.0
        assertEquals(7.0, rectangle.height)
    }

    @Test
    fun testSetNegativeWidthThrowsException() {
        val rectangle = Rectangle(5.0, 3.0)
        val exception = assertThrows<IllegalArgumentException> {
            rectangle.width = -1.0
        }
        assertEquals("Wrong width", exception.message)
    }

    @Test
    fun testSetNegativeHeightThrowsException() {
        val rectangle = Rectangle(5.0, 3.0)
        val exception = assertThrows<IllegalArgumentException> {
            rectangle.height = -2.0
        }
        assertEquals("Wrong height", exception.message)
    }

    @Test
    fun testSetZeroWidthThrowsException() {
        val rectangle = Rectangle(5.0, 3.0)
        val exception = assertThrows<IllegalArgumentException> {
            rectangle.width = 0.0
        }
        assertEquals("Wrong width", exception.message)
    }

    @Test
    fun testSetZeroHeightThrowsException() {
        val rectangle = Rectangle(5.0, 3.0)
        val exception = assertThrows<IllegalArgumentException> {
            rectangle.height = 0.0
        }
        assertEquals("Wrong height", exception.message)
    }

    @Test
    fun testAreaCalculationAfterModification() {
        val rectangle = Rectangle(5.0, 3.0)
        rectangle.width = 4.0
        rectangle.height = 6.0
        assertEquals(24.0, rectangle.area)
    }

    @Test
    fun testAreaIsReadOnly() {
        val rectangle = Rectangle(5.0, 3.0)
        assertEquals(15.0, rectangle.area)

        rectangle.width = 2.0
        assertEquals(6.0, rectangle.area)
    }
}