package my_project.tests.blurTests;

import backend.academy.blur.GaussianBlur;
import backend.academy.utilits.Pixel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GaussianBlurTest {

    private GaussianBlur gaussianBlur;
    private Pixel[][] testPixels;

    @BeforeEach
    public void setUp() {

        gaussianBlur = new GaussianBlur();
        testPixels = new Pixel[3][3];
        testPixels[0][0] = new Pixel(10, 20, 30, 1);
        testPixels[0][1] = new Pixel(40, 50, 60, 1);
        testPixels[0][2] = new Pixel(70, 80, 90, 1);
        testPixels[1][0] = new Pixel(100, 110, 120, 1);
        testPixels[1][1] = new Pixel(130, 140, 150, 1);
        testPixels[1][2] = new Pixel(160, 170, 180, 1);
        testPixels[2][0] = new Pixel(190, 200, 210, 1);
        testPixels[2][1] = new Pixel(220, 230, 240, 1);
        testPixels[2][2] = new Pixel(250, 260, 270, 1);
    }

    @Test
    public void testApplyBlur() {
        Pixel[][] result = gaussianBlur.applyBlur(testPixels);

        assertNotNull(result);
        assertEquals(3, result.length);
        assertEquals(3, result[0].length);

        Pixel middlePixel = result[1][1];
        assertNotNull(middlePixel);
        assertTrue(middlePixel.red() >= 0 && middlePixel.red() <= 255);
        assertTrue(middlePixel.green() >= 0 && middlePixel.green() <= 255);
        assertTrue(middlePixel.blue() >= 0 && middlePixel.blue() <= 255);
    }

    @Test
    public void testSumColorsNeighbours() {

        Pixel pixel = new Pixel(100, 150, 200, 1);
        Pixel resultPixel = gaussianBlur.sumColorsNeighbours(testPixels, 3, 3, 1, 1);

        assertNotNull(resultPixel);

        double expectedRed = 0;
        double expectedGreen = 0;
        double expectedBlue = 0;

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                int nx = 1 + dx;
                int ny = 1 + dy;
                if (nx >= 0 && nx < 3 && ny >= 0 && ny < 3) {
                    Pixel neighbor = testPixels[nx][ny];
                    double weight = gaussianBlur.kernel[dx + 1][dy + 1];
                    expectedRed += neighbor.red() * weight;
                    expectedGreen += neighbor.green() * weight;
                    expectedBlue += neighbor.blue() * weight;
                }
            }
        }

        assertEquals((int) expectedRed, resultPixel.red());
        assertEquals((int) expectedGreen, resultPixel.green());
        assertEquals((int) expectedBlue, resultPixel.blue());
    }

    @Test
    public void testEdgeHandling() {
        Pixel[][] result = gaussianBlur.applyBlur(testPixels);

        Pixel topLeftPixel = result[0][0];
        Pixel topRightPixel = result[0][2];
        Pixel bottomLeftPixel = result[2][0];
        Pixel bottomRightPixel = result[2][2];

        assertNotNull(topLeftPixel);
        assertNotNull(topRightPixel);
        assertNotNull(bottomLeftPixel);
        assertNotNull(bottomRightPixel);
    }

    @Test
    public void testSinglePixelImage() {
        Pixel[][] singlePixel = new Pixel[1][1];
        singlePixel[0][0] = new Pixel(255, 255, 255, 1);

        Pixel[][] result = gaussianBlur.applyBlur(singlePixel);

        assertNotNull(result);
        assertEquals(1, result.length);
        assertEquals(1, result[0].length);
        assertEquals(63, result[0][0].red());
        assertEquals(63, result[0][0].green());
        assertEquals(63, result[0][0].blue());
    }
}

