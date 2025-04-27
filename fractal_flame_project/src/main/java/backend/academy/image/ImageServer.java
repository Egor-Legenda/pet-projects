package backend.academy.image;

import backend.academy.print.ConsolePrinter;
import backend.academy.utilits.NumberUtil;
import backend.academy.utilits.Pixel;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;


public class ImageServer {
    private ConsolePrinter consolePrinter = new ConsolePrinter();

    public void saveImage(Pixel[][] pixels, String filename, String resolution, int height, int width) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pixel p = pixels[x][y];
                int red = Math.min(NumberUtil.COLOR_MAX.number(), (int) (p.red()));
                int green = Math.min(NumberUtil.COLOR_MAX.number(), (int) (p.green()));
                int blue = Math.min(NumberUtil.COLOR_MAX.number(), (int) (p.blue()));
                int rgb = (red << NumberUtil.SIXTEEN.number()) | (green << NumberUtil.EIGHT.number()) | blue;
                image.setRGB(x, y, rgb);
            }
        }

        try {
            ImageIO.write(image, resolution, new File(filename));
            consolePrinter.print("Изображение сохранено: " + filename);
        } catch (Exception e) {
            consolePrinter.printRed("Не удалось сохранить фото");
        }
    }

}
