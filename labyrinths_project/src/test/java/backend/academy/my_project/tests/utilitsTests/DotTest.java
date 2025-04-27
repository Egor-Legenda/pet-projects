package backend.academy.my_project.tests.utilitsTests;

import backend.academy.my_project.utilits.Dot;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DotTest {

    @Test
    @DisplayName("Test constructor dot")
    void testDotConstructor() {
        Dot dot = new Dot(5, 10);
        assertEquals(5, dot.x(), "X coordinate should be initialized correctly");
        assertEquals(10, dot.y(), "Y coordinate should be initialized correctly");
    }

    @Test
    @DisplayName("Test getter and setter for x")
    void testx() {
        Dot dot = new Dot(0, 0);
        dot.x(15);
        assertEquals(15, dot.x(), "X coordinate should be updated correctly");
    }

    @Test
    @DisplayName("Test getter and setter for y")
    void testy() {
        Dot dot = new Dot(0, 0);
        dot.y(20);
        assertEquals(20, dot.y(), "Y coordinate should be updated correctly");
    }

    @Test
    @DisplayName("Test to String")
    void testToString() {
        Dot dot = new Dot(3, 4);
        String expectedString = "Dot(x=3, y=4)";
        assertEquals(expectedString, dot.toString(), "ToString method should return correct string representation");
    }

}
