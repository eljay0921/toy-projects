package example.java.dataclass;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RectangleTest {

    @Test
    void testConstructor() {
        Rectangle rectangle = new Rectangle(5.0, 3.0);
        assertEquals(5.0, rectangle.getWidth());
        assertEquals(3.0, rectangle.getHeight());
    }

    @Test
    void testGetArea() {
        Rectangle rectangle = new Rectangle(5.0, 3.0);
        assertEquals(15.0, rectangle.getArea());
    }

    @Test
    void testSetValidWidth() {
        Rectangle rectangle = new Rectangle(5.0, 3.0);
        rectangle.setWidth(10.0);
        assertEquals(10.0, rectangle.getWidth());
    }

    @Test
    void testSetValidHeight() {
        Rectangle rectangle = new Rectangle(5.0, 3.0);
        rectangle.setHeight(7.0);
        assertEquals(7.0, rectangle.getHeight());
    }

    @Test
    void testSetNegativeWidthThrowsException() {
        Rectangle rectangle = new Rectangle(5.0, 3.0);
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> rectangle.setWidth(-1.0)
        );
        assertEquals("Wrong width!", exception.getMessage());
    }

    @Test
    void testSetNegativeHeightThrowsException() {
        Rectangle rectangle = new Rectangle(5.0, 3.0);
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> rectangle.setHeight(-2.0)
        );
        assertEquals("Wrong height!", exception.getMessage());
    }

    @Test
    void testAreaCalculationAfterModification() {
        Rectangle rectangle = new Rectangle(5.0, 3.0);
        rectangle.setWidth(4.0);
        rectangle.setHeight(6.0);
        assertEquals(24.0, rectangle.getArea());
    }

    @Test
    void testZeroValues() {
        Rectangle rectangle = new Rectangle(0.0, 0.0);
        assertEquals(0.0, rectangle.getWidth());
        assertEquals(0.0, rectangle.getHeight());
        assertEquals(0.0, rectangle.getArea());
    }
}