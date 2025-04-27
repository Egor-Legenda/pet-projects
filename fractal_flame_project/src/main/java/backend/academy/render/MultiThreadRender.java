package backend.academy.render;

import backend.academy.utilits.NumberUtil;
import backend.academy.utilits.Pixel;
import backend.academy.utilits.TransformationParameters;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@SuppressWarnings({"checkstyle:LambdaBodyLength", "checkstyle:CyclomaticComplexity", "checkstyle:NestedForDepth"})
@SuppressFBWarnings({"DMI_RANDOM_USED_ONLY_ONCE", "PSC_PRESIZE_COLLECTIONS"})
public class MultiThreadRender implements Render {
    private static final double Y_MIN = -1;
    private static final double Y_MAX = 1;
    private static final double X_MIN = -1.777;
    private static final double X_MAX = 1.777;

    @Override
    public Pixel[][] render(
        int height, int width, int n, int eqCount,
        int iteration, List<Integer> transformations, double symmetryAxes
    ) {
        Pixel[][] pixels = new Pixel[width][height];
        TransformationParameters[] transformationParameters = generateTransformationParameters(eqCount);
        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        int rowsPerThread = height / numThreads;
        List<Future<Pixel[][]>> futures = new ArrayList<>();

        for (int threadId = 0; threadId < numThreads; threadId++) {
            final int startRow = threadId * rowsPerThread;
            final int endRow = (threadId == numThreads - 1) ? height : startRow + rowsPerThread;
            futures.add(executor.submit(() -> {
                Pixel[][] threadPixels = new Pixel[width][endRow - startRow];
                for (int i = 0; i < width; i++) {
                    for (int j = 0; j < endRow - startRow; j++) {
                        threadPixels[i][j] = new Pixel();
                    }
                }

                SecureRandom threadRandom = new SecureRandom();
                for (int num = 0; num < n / numThreads; num++) {
                    double newX = rand(X_MIN, X_MAX);
                    double newY = rand(Y_MIN, Y_MAX);

                    for (int step = NumberUtil.START_CYCLE.number(); step < iteration; step++) {
                        int i = threadRandom.nextInt(eqCount);
                        TransformationParameters t = transformationParameters[i];
                        double x = t.a() * newX + t.b() * newY + t.c();
                        double y = t.d() * newX + t.e() * newY + t.f();

                        for (Integer j : transformations) {
                            newX = TRANSFORMATION_HASH_MAP.get(j).transformX(x, y);
                            newY = TRANSFORMATION_HASH_MAP.get(j).transformY(x, y);

                        }

                        if (step >= 0 && newX >= X_MIN && newX <= X_MAX && newY >= Y_MIN && newY <= Y_MAX) {
                            int x1 = (int) ((X_MAX - newX) / (X_MAX - X_MIN) * width);
                            int y1 = (int) ((Y_MAX - newY) / (Y_MAX - Y_MIN) * height) - startRow;

                            if (x1 >= 0 && x1 < width && y1 >= 0 && y1 < (endRow - startRow)) {
                                applyPixelColor(threadPixels, x1, y1, t);
                            }


                            if (symmetryAxes > 1) {
                                symmetryAxes(symmetryAxes, threadPixels, height, width,
                                t, endRow, startRow, newX, newY);
                            }
                        }
                    }
                }
                return threadPixels;
            }));
        }
        for (int threadId = 0; threadId < numThreads; threadId++) {
            try {
                Pixel[][] threadPixels = futures.get(threadId).get();
                int startRow = threadId * rowsPerThread;
                for (int i = 0; i < width; i++) {
                    System.arraycopy(threadPixels[i], 0, pixels[i], startRow, threadPixels[i].length);
                }
            } catch (Exception e) {
                throw new RuntimeException("Ошибка во время обработки потока", e);
            }
        }

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Рендеринг был прерван", e);
        }
        return pixels;
    }

    @Override
    public String name() {
        return "Многопоточная генерация";
    }

}
