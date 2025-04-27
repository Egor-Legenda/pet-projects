package my_project.tests.imageTests;

import backend.academy.image.ImageServer;
import backend.academy.print.ConsolePrinter;
import backend.academy.utilits.Pixel;
import org.junit.jupiter.api.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ImageServerTest {

    private ImageServer imageServer;
    private ConsolePrinter consolePrinterMock;

    @BeforeEach
    void setUp() {
        consolePrinterMock = mock(ConsolePrinter.class);
        imageServer = new ImageServer();
        try {
            var consolePrinterField = ImageServer.class.getDeclaredField("consolePrinter");
            consolePrinterField.setAccessible(true);
            consolePrinterField.set(imageServer, consolePrinterMock);
        } catch (Exception e) {
            throw new RuntimeException("Failed to inject mock ConsolePrinter", e);
        }
    }

    @Test
    void testSaveImageSuccess() throws Exception {
        int width = 2;
        int height = 2;
        Pixel[][] pixels = {
            {new Pixel(255, 0, 0, 0), new Pixel(0, 255, 0, 0)},
            {new Pixel(0, 0, 255, 0), new Pixel(255, 255, 255, 0)}
        };

        File tempFile = Files.createTempFile("test_image", ".png").toFile();
        tempFile.deleteOnExit();

        imageServer.saveImage(pixels, tempFile.getAbsolutePath(), "png", height, width);

        BufferedImage savedImage = ImageIO.read(tempFile);
        assertNotNull(savedImage, "Изображение должно быть создано");
        assertEquals(width, savedImage.getWidth());
        assertEquals(height, savedImage.getHeight());

        assertEquals(0xFF0000, savedImage.getRGB(0, 0) & 0xFFFFFF);
        assertEquals(0x00FF00, savedImage.getRGB(0, 1) & 0xFFFFFF);
        assertEquals(0x0000FF, savedImage.getRGB(1, 0) & 0xFFFFFF);
        assertEquals(0xFFFFFF, savedImage.getRGB(1, 1) & 0xFFFFFF);

        verify(consolePrinterMock).print("Изображение сохранено: " + tempFile.getAbsolutePath());
    }

    @Test
    void testRgbCalculation() {
        Pixel[][] pixels = {
            {new Pixel(300, -10, 500, 0)},
        };

        File tempFile = new File("test_rgb.png");

        imageServer.saveImage(pixels, tempFile.getAbsolutePath(), "png", 1, 1);

        try {
            BufferedImage savedImage = ImageIO.read(tempFile);
            assertNotNull(savedImage, "Изображение должно быть создано");
            int rgb = savedImage.getRGB(0, 0) & 0xFFFFFF;
            assertEquals(16774911, rgb, "Цвет должен быть ограничен до максимального значения");

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            tempFile.delete();
        }
    }
}

