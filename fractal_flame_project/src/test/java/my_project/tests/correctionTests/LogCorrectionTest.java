package my_project.tests.correctionTests;

import backend.academy.correction.LogCorrection;
import backend.academy.utilits.Pixel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LogCorrectionTest {

    private LogCorrection logCorrection;
    private Pixel[][] testPixels;

    @BeforeEach
    public void setUp() {
        logCorrection = new LogCorrection();
        testPixels = new Pixel[3][3];
        testPixels[0][0] = new Pixel(10, 20, 30, 10);
        testPixels[0][1] = new Pixel(40, 50, 60, 20);
        testPixels[0][2] = new Pixel(70, 80, 90, 30);
        testPixels[1][0] = new Pixel(100, 110, 120, 40);
        testPixels[1][1] = new Pixel(130, 140, 150, 50);
        testPixels[1][2] = new Pixel(160, 170, 180, 60);
        testPixels[2][0] = new Pixel(190, 200, 210, 70);
        testPixels[2][1] = new Pixel(220, 230, 240, 80);
        testPixels[2][2] = new Pixel(250, 260, 270, 90);
    }

    @Test
    public void testApplyLogCorrection() {
        double gamma = 1.0;
        Pixel[][] result = logCorrection.applyLogCorrectionToImage(testPixels, gamma);
        assertNotNull(result);
        assertEquals(3, result.length);
        assertEquals(3, result[0].length);
        Pixel firstPixel = result[0][0];
        assertNotNull(firstPixel);
        assertTrue(firstPixel.red() >= 0 && firstPixel.red() <= 255);
        assertTrue(firstPixel.green() >= 0 && firstPixel.green() <= 255);
        assertTrue(firstPixel.blue() >= 0 && firstPixel.blue() <= 255);
        assertEquals(testPixels[0][0].red(), firstPixel.red());
        assertEquals(testPixels[0][0].green(), firstPixel.green());
        assertEquals(testPixels[0][0].blue(), firstPixel.blue());
    }

    @Test
    public void testApplyLogCorrectionWithGamma() {
        double gamma = 2.0;
        Pixel[][] result = logCorrection.applyLogCorrectionToImage(testPixels, gamma);

        Pixel firstPixel = result[0][0];
        assertNotNull(firstPixel);
        assertTrue(firstPixel.red() >= 0 && firstPixel.red() <= 255);
        assertTrue(firstPixel.green() >= 0 && firstPixel.green() <= 255);
        assertTrue(firstPixel.blue() >= 0 && firstPixel.blue() <= 255);

    }

    @Test
    public void testFindMaxIntensity() {

        double maxIntensity = logCorrection.findMaxIntensity(testPixels);

        double expectedMax = Math.log10(90);

        assertEquals(expectedMax, maxIntensity, 0.0001);
    }

    @Test
    public void testEdgeCaseZeroCounter() {

        Pixel[][] testPixelsWithZeroCounter = new Pixel[2][2];
        testPixelsWithZeroCounter[0][0] = new Pixel(10, 20, 30, 0);
        testPixelsWithZeroCounter[0][1] = new Pixel(40, 50, 60, 20);
        testPixelsWithZeroCounter[1][1] = new Pixel(130, 140, 150, 50);
        testPixelsWithZeroCounter[1][0] = new Pixel(250, 260, 270, 90);

        double gamma = 1.0;
        Pixel[][] result = logCorrection.applyLogCorrectionToImage(testPixelsWithZeroCounter, gamma);

        Pixel zeroCounterPixel = result[0][0];
        assertEquals(testPixelsWithZeroCounter[0][0].red(), zeroCounterPixel.red());
        assertEquals(testPixelsWithZeroCounter[0][0].green(), zeroCounterPixel.green());
        assertEquals(testPixelsWithZeroCounter[0][0].blue(), zeroCounterPixel.blue());
    }

    @Test
    public void testSinglePixelImage() {
        Pixel[][] singlePixelImage = new Pixel[1][1];
        singlePixelImage[0][0] = new Pixel(100, 150, 200, 10);

        double gamma = 1.0;
        Pixel[][] result = logCorrection.applyLogCorrectionToImage(singlePixelImage, gamma);
        Pixel resultPixel = result[0][0];
        assertNotNull(resultPixel);
        assertTrue(resultPixel.red() >= 0 && resultPixel.red() <= 255);
        assertTrue(resultPixel.green() >= 0 && resultPixel.green() <= 255);
        assertTrue(resultPixel.blue() >= 0 && resultPixel.blue() <= 255);
    }
}

