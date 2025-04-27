package backend.academy.correction;


import backend.academy.utilits.Pixel;

public class LogCorrection {

    public Pixel[][] applyLogCorrectionToImage(Pixel[][] pixels, double gamma) {
        int width = pixels.length;
        int height = pixels[0].length;
        double maxIntensity = findMaxIntensity(pixels);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (pixels[x][y].counter() != 0) {
                    double normalized = Math.log10(pixels[x][y].counter()) / maxIntensity;

                    double gammaCorrected = Math.pow(normalized, 1.0 / gamma);
                    int correctedRed = (int) (pixels[x][y].red() * gammaCorrected);
                    int correctedGreen = (int) (pixels[x][y].green() * gammaCorrected);
                    int correctedBlue = (int) (pixels[x][y].blue() * gammaCorrected);

                    pixels[x][y] = new Pixel(correctedRed, correctedGreen, correctedBlue, pixels[x][y].counter());

                }
            }
        }
        return pixels;
    }

    public double findMaxIntensity(Pixel[][] pixels) {
        double max = 0.0;
        for (Pixel[] row : pixels) {
            for (Pixel pixel : row) {
                if (pixel.counter() != 0) {
                    if (Math.log10(pixel.counter()) > max) {
                        max = Math.log10(pixel.counter());
                    }
                }
            }
        }
        return max;
    }
}
