package my_project.tests.blurTests;

import backend.academy.utilits.Pixel;
import backend.academy.blur.CompletionBlur;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CompletionBlurTest {

    private final CompletionBlur completionBlur = new CompletionBlur();

    @Test
    void testApplyBlurWithNoEmptyPixels() {
        Pixel[][] pixels = {
            {new Pixel(100, 100, 100, 1), new Pixel(200, 200, 200, 1)},
            {new Pixel(150, 150, 150, 1), new Pixel(250, 250, 250, 1)}
        };

        Pixel[][] result = completionBlur.applyBlur(pixels);
        assertArrayEquals(pixels, result);
    }

    @Test
    void testApplyBlurWithEmptyPixel() {
        Pixel[][] pixels = {
            {new Pixel(100, 100, 100, 1), new Pixel(200, 200, 200, 1)},
            {new Pixel(150, 150, 150, 1), new Pixel(0, 0, 0, 0)} // Пустой пиксель
        };

        Pixel[][] result = completionBlur.applyBlur(pixels);

        Pixel filledPixel = result[1][1];
        assertEquals(150, filledPixel.red());
        assertEquals(150, filledPixel.green());
        assertEquals(150, filledPixel.blue());
    }

    @Test
    void testApplyBlurWithMultipleEmptyPixels() {
        Pixel[][] pixels = {
            {new Pixel(100, 0, 0, 1), new Pixel(200, 0, 0, 1)},
            {new Pixel(0, 0, 0, 0), new Pixel(0, 0, 0, 0)} // Два пустых пикселя
        };

        Pixel[][] result = completionBlur.applyBlur(pixels);
        Pixel firstEmpty = result[1][0];
        assertEquals(150, firstEmpty.red());
        assertEquals(0, firstEmpty.green());
        assertEquals(0, firstEmpty.blue());
        Pixel secondEmpty = result[1][1];
        assertEquals(150, secondEmpty.red());
        assertEquals(0, secondEmpty.green());
        assertEquals(0, secondEmpty.blue());
    }

    @Test
    void testSumColorsNeighbours() {
        Pixel[][] pixels = {
            {new Pixel(100, 50, 25, 1), new Pixel(200, 100, 50, 1)},
            {new Pixel(0, 0, 0, 0), new Pixel(50, 25, 12, 1)}
        };

        Pixel result = completionBlur.sumColorsNeighbours(pixels, 2, 2, 1, 0);
        assertEquals(116, result.red());
        assertEquals(58, result.green());
        assertEquals(29, result.blue());
    }
}

