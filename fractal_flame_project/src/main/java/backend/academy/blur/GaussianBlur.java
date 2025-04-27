package backend.academy.blur;

import backend.academy.utilits.NumberUtil;
import backend.academy.utilits.Pixel;

public class GaussianBlur implements Blur {
    public double[][] kernel = {
        {(double) 1 / NumberUtil.SIXTEEN.number(),
            (double) 1 / NumberUtil.EIGHT.number(), (double) 1 / NumberUtil.SIXTEEN.number()},
        {(double) 1 / NumberUtil.EIGHT.number(),
            (double) 1 / NumberUtil.FOUR.number(), (double) 1 / NumberUtil.EIGHT.number()},
        {(double) 1 / NumberUtil.SIXTEEN.number(),
            (double) 1 / NumberUtil.EIGHT.number(), (double) 1 / NumberUtil.SIXTEEN.number()}
    };

    @Override
    public Pixel[][] applyBlur(Pixel[][] pixels) {
        int width = pixels.length;
        int height = pixels[0].length;

        Pixel[][] result = new Pixel[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

                result[x][y] = sumColorsNeighbours(pixels, height, width, x, y);
            }
        }

        return result;
    }

    public Pixel sumColorsNeighbours(Pixel[][] pixels, int height, int width, int x, int y) {
        double redSum = 0;
        double greenSum = 0;
        double blueSum = 0;
        int count = 0;

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                int nx = x + dx;
                int ny = y + dy;
                if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                    Pixel neighbor = pixels[nx][ny];
                    double weight = kernel[dx + 1][dy + 1];
                    redSum += neighbor.red() * weight;
                    greenSum += neighbor.green() * weight;
                    blueSum += neighbor.blue() * weight;
                    count++;
                }
            }
        }
        return new Pixel((int) redSum, (int) greenSum, (int) blueSum, pixels[x][y].counter());
    }
}
