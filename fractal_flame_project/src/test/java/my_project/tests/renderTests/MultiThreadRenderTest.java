package my_project.tests.renderTests;

import backend.academy.render.MultiThreadRender;
import backend.academy.utilits.Pixel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MultiThreadRenderTest {

    private MultiThreadRender render;

    @BeforeEach
    void setUp() {
        render = new MultiThreadRender();
    }

    @Test
    void testRenderBasic() {
        int width = 10;
        int height = 10;
        int n = 1000;
        int eqCount = 3;
        int iterations = 10;
        List<Integer> transformations = new ArrayList<>();
        double symmetryAxes = 1;

        Pixel[][] result = render.render(height, width, n, eqCount, iterations, transformations, symmetryAxes);

        assertNotNull(result, "Render result should not be null");

        assertEquals(width, result.length, "Render result width should match the input width");
        assertEquals(height, result[0].length, "Render result height should match the input height");

        for (Pixel[] row : result) {
            for (Pixel pixel : row) {
                assertNotNull(pixel, "Each pixel in the render result should be initialized");
            }
        }
    }

    @Test
    void testRenderWithMinimalDimensions() {
        int width = 1;
        int height = 1;
        int n = 1;
        int eqCount = 1;
        int iterations = 1;
        List<Integer> transformations = new ArrayList<>();
        double symmetryAxes = 1;

        Pixel[][] result = render.render(height, width, n, eqCount, iterations, transformations, symmetryAxes);

        assertNotNull(result);
        assertEquals(1, result.length);
        assertEquals(1, result[0].length);
        assertNotNull(result[0][0]);
    }

    @Test
    void testRenderWithZeroIterations() {
        int width = 10;
        int height = 10;
        int n = 100;
        int eqCount = 3;
        int iterations = 0;
        List<Integer> transformations = new ArrayList<>();
        double symmetryAxes = 1;

        Pixel[][] result = render.render(height, width, n, eqCount, iterations, transformations, symmetryAxes);

        for (Pixel[] row : result) {
            for (Pixel pixel : row) {
                assertEquals(0, pixel.counter(), "Pixel counter should remain 0 for zero iterations");
            }
        }
    }

    @Test
    void testRenderWithLargeInput() {
        int width = 1000;
        int height = 1000;
        int n = 100000;
        int eqCount = 5;
        int iterations = 100;
        List<Integer> transformations = new ArrayList<>();
        double symmetryAxes = 1;

        Pixel[][] result = render.render(height, width, n, eqCount, iterations, transformations, symmetryAxes);

        assertNotNull(result);
        assertEquals(width, result.length);
        assertEquals(height, result[0].length);
    }

}

