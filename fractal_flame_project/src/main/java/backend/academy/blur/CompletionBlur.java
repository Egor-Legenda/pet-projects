package backend.academy.blur;

import backend.academy.utilits.Pixel;

public class CompletionBlur implements Blur {
    @Override
    public Pixel[][] applyBlur(Pixel[][] pixels) {
        int width = pixels.length;
        int height = pixels[0].length;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pixel pixel = pixels[x][y];
                if (pixel.counter() == 0) {
                    pixels[x][y] = sumColorsNeighbours(pixels, height, width, x, y);
                }
            }
        }

        return pixels;
    }

    public Pixel sumColorsNeighbours(Pixel[][] pixels, int height, int width, int x, int y) {
        int redSum = 0;
        int greenSum = 0;
        int blueSum = 0;
        int count = 0;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                int nx = x + dx;
                int ny = y + dy;
                if (nx >= 0 && nx < width && ny >= 0 && ny < height && pixels[nx][ny].counter() > 0) {
                    Pixel neighbor = pixels[nx][ny];
                    redSum += neighbor.red();
                    greenSum += neighbor.green();
                    blueSum += neighbor.blue();
                    count++;
                }
            }
        }
        if (count > 0) {
            return new Pixel(redSum / count, greenSum / count, blueSum / count, 0);
        }
        return pixels[x][y];
    }

}
