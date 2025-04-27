package my_project.tests.utilitsTests;

import backend.academy.utilits.Pixel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PixelTest {

    @Test
    void testDefaultConstructor() {
        Pixel pixel = new Pixel();

        assertEquals(0, pixel.red(), "Red channel should be initialized to 0");
        assertEquals(0, pixel.green(), "Green channel should be initialized to 0");
        assertEquals(0, pixel.blue(), "Blue channel should be initialized to 0");
        assertEquals(0, pixel.counter(), "Counter should be initialized to 0");
    }

    @Test
    void testParameterizedConstructor() {
        Pixel pixel = new Pixel(100, 150, 200, 5);

        assertEquals(100, pixel.red());
        assertEquals(150, pixel.green());
        assertEquals(200, pixel.blue());
        assertEquals(5, pixel.counter());
    }

    @Test
    void testSettersAndGetters() {
        Pixel pixel = new Pixel();

        pixel.red(255);
        pixel.green(128);
        pixel.blue(64);
        pixel.counter(10);

        assertEquals(255, pixel.red(), "Red channel should be 255");
        assertEquals(128, pixel.green(), "Green channel should be 128");
        assertEquals(64, pixel.blue(), "Blue channel should be 64");
        assertEquals(10, pixel.counter(), "Counter should be 10");
    }

    @Test
    void testNegativeValues() {
        Pixel pixel = new Pixel();

        pixel.red(-50);
        pixel.green(-100);
        pixel.blue(-150);
        pixel.counter(-5);

        assertEquals(-50, pixel.red(), "Red channel should be -50");
        assertEquals(-100, pixel.green(), "Green channel should be -100");
        assertEquals(-150, pixel.blue(), "Blue channel should be -150");
        assertEquals(-5, pixel.counter(), "Counter should be -5");
    }

    @Test
    void testExtremeValues() {
        Pixel pixel = new Pixel();
        pixel.red(Integer.MAX_VALUE);
        pixel.green(Integer.MIN_VALUE);
        pixel.blue(Integer.MAX_VALUE);
        pixel.counter(Integer.MIN_VALUE);

        assertEquals(Integer.MAX_VALUE, pixel.red(), "Red channel should be Integer.MAX_VALUE");
        assertEquals(Integer.MIN_VALUE, pixel.green(), "Green channel should be Integer.MIN_VALUE");
        assertEquals(Integer.MAX_VALUE, pixel.blue(), "Blue channel should be Integer.MAX_VALUE");
        assertEquals(Integer.MIN_VALUE, pixel.counter(), "Counter should be Integer.MIN_VALUE");
    }
}

