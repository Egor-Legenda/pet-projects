package backend.academy.render;

import backend.academy.utilits.NumberUtil;
import backend.academy.utilits.Pixel;
import backend.academy.utilits.TransformationParameters;
import java.security.SecureRandom;
import java.util.List;

@SuppressWarnings({"checkstyle:CyclomaticComplexity"})
public class SingleThreadRender implements Render {
    private SecureRandom secureRandom = new SecureRandom();
    private static final double Y_MIN = -1;
    private static final double Y_MAX = 1;
    private static final double X_MIN = -1.777;
    private static final double X_MAX = 1.777;

    @Override
    public Pixel[][] render(
        int height, int width, int n,
        int eqCount, int iteration, List<Integer> transformations, double symmetryAxes
    ) {
        Pixel[][] pixels = new Pixel[width][height];
        TransformationParameters[] transformationParameters = generateTransformationParameters(eqCount);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                pixels[i][j] = new Pixel();
            }
        }
        for (int num = 0; num < n; num++) {
            double newX = rand(X_MIN, X_MAX);
            double newY = rand(Y_MIN, Y_MAX);

            for (int step = NumberUtil.START_CYCLE.number(); step < iteration; step++) {

                int i = secureRandom.nextInt(eqCount);
                TransformationParameters t = transformationParameters[i];

                double y = (t.d() * newX + t.e() * newY + t.f());
                double x = t.a() * newX + t.b() * newY + t.c();


                for (Integer j : transformations) {
                    newY = TRANSFORMATION_HASH_MAP.get(j).transformY(x, y);
                    newX = TRANSFORMATION_HASH_MAP.get(j).transformX(x, y);

                }


                if (step >= 0 && newX >= X_MIN && newX <= X_MAX && newY >= Y_MIN && newY <= Y_MAX) {
                    int x1 = (int) ((X_MAX - newX) / (X_MAX - X_MIN) * width);
                    int y1 = (int) ((Y_MAX - newY) / (Y_MAX - Y_MIN) * height);
                    if (x1 >= 0 && x1 < width && y1 >= 0 && y1 < height) {

                        applyPixelColor(pixels, x1, y1, t);
                    }
                    if (symmetryAxes > 1) {
                        symmetryAxes(symmetryAxes, pixels, height, width,
                            t, height, 0, newX, newY);
                    }
                }
            }

        }
        return pixels;

    }

    @Override
    public String name() {
        return "Однопоточная генерация";
    }


}
