package backend.academy.render;

import backend.academy.transformation.Transformation;
import backend.academy.transformation.TransformationDisc;
import backend.academy.transformation.TransformationEyefish;
import backend.academy.transformation.TransformationHeart;
import backend.academy.transformation.TransformationHorseshoe;
import backend.academy.transformation.TransformationHyperbolic;
import backend.academy.transformation.TransformationSinusoidal;
import backend.academy.transformation.TransformationSwirl;
import backend.academy.utilits.NumberUtil;
import backend.academy.utilits.Pixel;
import backend.academy.utilits.TransformationParameters;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;

@SuppressWarnings("checkstyle:ParameterNumber")
public interface Render {
    SecureRandom SECURE_RANDOM = new SecureRandom();
    double RANGE = 0.5;
    double Y_MIN = -1;
    double Y_MAX = 1;
    double X_MIN = -1.777;
    double X_MAX = 1.777;
    Map<Integer, Transformation> TRANSFORMATION_HASH_MAP = (Map.of(1, new TransformationDisc(),
        2, new TransformationHeart(), 3, new TransformationHorseshoe(), 4, new TransformationSwirl(),
        5, new TransformationSinusoidal(), 6, new TransformationEyefish(), 7, new TransformationHyperbolic()));

    Pixel[][] render(int height, int width, int n, int eqCount,
        int iteration, List<Integer> transformations, double symmetryAxes);

    String name();

    default TransformationParameters[] generateTransformationParameters(int eqCount) {
        TransformationParameters[] transformationParameters = new TransformationParameters[eqCount];
        double a;
        double b;
        double c;
        double d;
        double e;
        double f;
        for (int i = 0; i < eqCount; i += 1) {
            do {
                a = rand(-1, 1);
                b = rand(-1, 1);
                c = rand(-RANGE, RANGE);
                d = rand(-1, 1);
                e = rand(-1, 1);
                f = rand(-RANGE, RANGE);
            } while (!((a * a + d * d) < 1 && (b * b + e * e) < 1
                && (a * a + b * b + d * d + e * e) < (1 + (a * e - b * d) * (a * e - b * d))));
            int red = SECURE_RANDOM.nextInt(256);
            int green = SECURE_RANDOM.nextInt(256);
            int blue = SECURE_RANDOM.nextInt(256);
            transformationParameters[i] = new TransformationParameters(a, b, c, d, e, f, red, green, blue);
        }
        return transformationParameters;
    }

    default double rand(double min, double max) {
        return SECURE_RANDOM.nextDouble(min, max);
    }

    default void applyPixelColor(Pixel[][] pixels, int x, int y, TransformationParameters t) {
        if (pixels[x][y].counter() == 0) {
            pixels[x][y].red(t.red());
            pixels[x][y].green(t.green());
            pixels[x][y].blue(t.blue());
        } else {
            pixels[x][y].red((pixels[x][y].red() + t.red()) / 2);
            pixels[x][y].green((pixels[x][y].green() + t.green()) / 2);
            pixels[x][y].blue((pixels[x][y].blue() + t.blue()) / 2);
        }
        pixels[x][y].counter(pixels[x][y].counter() + 1);
    }

    default void symmetryAxes(double symmetryAxes, Pixel[][] threadPixels, int height, int width,
        TransformationParameters t, int endRow, int startRow, double newX, double newY) {
        double symmetryAngle = NumberUtil.DEGREE.number() / symmetryAxes;
        for (int k = 1; k < symmetryAxes; k++) {
            double angle = Math.toRadians(k * symmetryAngle);

            double symX = newX * Math.cos(angle) - newY * Math.sin(angle);
            double symY = newX * Math.sin(angle) + newY * Math.cos(angle);

            int symX1 = (int) ((X_MAX - symX) / (X_MAX - X_MIN) * width);
            int symY1 = (int) ((Y_MAX - symY) / (Y_MAX - Y_MIN) * height) - startRow;
            if (symX1 >= 0 && symX1 < width && symY1 >= 0 && symY1 < (endRow - startRow)) {
                applyPixelColor(threadPixels, symX1, symY1, t);
            }
        }
    }

}
