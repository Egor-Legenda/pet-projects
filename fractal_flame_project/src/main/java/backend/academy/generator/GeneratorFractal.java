package backend.academy.generator;

import backend.academy.blur.Blur;
import backend.academy.blur.CompletionBlur;
import backend.academy.blur.GaussianBlur;
import backend.academy.correction.LogCorrection;
import backend.academy.image.ImageServer;
import backend.academy.print.ConsolePrinter;
import backend.academy.print.Printer;
import backend.academy.reader.Reader;
import backend.academy.reader.ReaderLine;
import backend.academy.render.MultiThreadRender;
import backend.academy.render.Render;
import backend.academy.render.SingleThreadRender;
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
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressFBWarnings("DMC_DUBIOUS_MAP_COLLECTION")
public class GeneratorFractal {
    private Reader readerLine = new ReaderLine();
    private Printer consolePrinter = new ConsolePrinter();
    private ImageServer imageServer = new ImageServer();
    private LogCorrection logCorrection = new LogCorrection();
    private Map<Integer, Render> renderMap = new HashMap<>(Map.of(1, new SingleThreadRender(),
        2, new MultiThreadRender()));
    private Map<Integer, Blur> blurMap = new HashMap<>(Map.of(1, new CompletionBlur(), 2, new GaussianBlur()));
    private static final double GAMMA = 2.2;
    private Map<Integer, Transformation> transformationMap = new HashMap<>(Map.of(
        NumberUtil.ONE.number(), new TransformationDisc(),
        NumberUtil.TWO.number(), new TransformationHeart(), NumberUtil.THREE.number(), new TransformationHorseshoe(),
        NumberUtil.FOUR.number(), new TransformationSwirl(), NumberUtil.FIFE.number(), new TransformationSinusoidal(),
        NumberUtil.SIX.number(), new TransformationEyefish(),
        NumberUtil.SEVEN.number(), new TransformationHyperbolic()));

    public void generate() {
        consolePrinter.println("Давайте подберем нужные вам параметры дле генерации");
        Render render = inRender();
        int height = inHeight();
        int width = inWidth();
        List<Integer> transformations = inTransformations();
        int eqCount = inEqCount();
        int iteration = inIteration();
        int symmetryAxes = inSymmetryAxes();
        boolean correction = inCorrection();
        Blur blur = inBlur();
        if (render != null) {

            Pixel[][] pixels = render.render(height, width, NumberUtil.THOUSAND.number(),
                eqCount, iteration, transformations, symmetryAxes);
            if (correction) {
                pixels = logCorrection.applyLogCorrectionToImage(pixels, GAMMA);
            }
            if (blur != null) {
                pixels = blur.applyBlur(pixels);
            }
            imageServer.saveImage(pixels, "fractal.png", "png", height, width);
        } else {
            for (Render r : renderMap.values()) {
                long startTime = System.nanoTime();
                Pixel[][] pixels =
                    r.render(height, width, NumberUtil.THOUSAND.number(),
                        eqCount, iteration, transformations, symmetryAxes);
                if (correction) {
                    pixels = logCorrection.applyLogCorrectionToImage(pixels, GAMMA);
                }
                if (blur != null) {
                    pixels = blur.applyBlur(pixels);
                }
                long endTime = System.nanoTime();
                long duration = (endTime - startTime) / NumberUtil.MILLION.number() / NumberUtil.THOUSAND.number();
                long m = (endTime - startTime) / NumberUtil.MILLION.number();
                consolePrinter.println(r.name() + " была выполнена за " + duration
                    + " секунды или же " + m + " миллисекунд");
            }
        }
    }

    public int inHeight() {
        consolePrinter.print("Введите высоту: ");
        int inHeight = 0;
        boolean flag = false;
        do {
            try {
                inHeight = Integer.parseInt(readerLine.read());
                if (inHeight > 0) {
                    flag = true;
                } else {
                    consolePrinter.print("Введите положительное число больше 0(высоту): ");
                }

            } catch (NumberFormatException e) {
                consolePrinter.print("Это число должно быть положительным и больше 0. Введите повторно высоту: ");
            }

        } while (!flag);
        return inHeight;
    }

    public int inWidth() {
        consolePrinter.print("Введите ширину: ");
        int inWidth = 0;
        boolean flag = false;
        do {
            try {
                inWidth = Integer.parseInt(readerLine.read());
                if (inWidth > 0) {
                    flag = true;
                } else {
                    consolePrinter.print("Введите положительное число больше 0(ширину): ");
                }

            } catch (NumberFormatException e) {
                consolePrinter.print("Это число должно быть. Введите повторно ширину: ");
            }

        } while (!flag);
        return inWidth;
    }

    public int inEqCount() {
        consolePrinter.print("Введите количество возможных афинных преобразований: ");
        int eqCount = 0;
        boolean flag = false;
        do {
            try {
                eqCount = Integer.parseInt(readerLine.read());
                if (eqCount > 0) {
                    flag = true;
                } else {
                    consolePrinter.print("Введите положительное число больше 0(афинные преобразования): ");
                }
            } catch (NumberFormatException e) {
                consolePrinter.print("Это должно быть число. Повторите ввод(афинные пространства): ");
            }
        } while (!flag);
        return eqCount;
    }

    public int inSymmetryAxes() {
        consolePrinter.print("Введите количество осей симметрии (например, 1, 2, 3 и т.д.): ");
        int axes = 0;
        boolean flag = false;
        do {
            try {
                axes = Integer.parseInt(readerLine.read());
                if (axes > 0) {
                    flag = true;
                } else {
                    consolePrinter.print("Введите положительное число больше 0: ");
                }
            } catch (NumberFormatException e) {
                consolePrinter.print("Это должно быть числом. Повторите ввод: ");
            }
        } while (!flag);
        return axes;
    }

    public boolean inCorrection() {
        consolePrinter.print("Введите нужна ли вам коррекция(+,-): ");
        boolean inCorrection = false;
        boolean flag = false;
        do {

            String str = (readerLine.read());
            if ("-".equals(str)) {
                flag = true;

            } else if ("+".equals(str)) {
                flag = true;
                inCorrection = true;
            } else {

                consolePrinter.print("Введите повторно нужна ли вам коррекция: ");
            }

        } while (!flag);
        return inCorrection;
    }

    public int inIteration() {
        int inIteration = 0;
        consolePrinter.print("Введите количество итераций: ");
        boolean flag = false;
        do {
            try {
                inIteration = Integer.parseInt(readerLine.read());
                if (inIteration > 0) {
                    flag = true;
                } else {
                    consolePrinter.print("Введите положительное число больше 0(итерации): ");
                }

            } catch (NumberFormatException e) {
                consolePrinter.print(
                    "Это число должно быть положительным и больше 0. Введите повторно количество итераций: ");
            }

        } while (!flag);
        return inIteration;
    }

    public List<Integer> inTransformations() {
        consolePrinter.println("""
            Функции:
            1 - Диск
            2 - Сердце
            3 - Подкова
            4 - Вихрь
            5 - Синусоидальное
            6 - глаз рыбы
            7 - гиперболическая""");
        consolePrinter.print(
            "Введите порядок трансформаций через пробел в том порядке в каком они должны быть применены: ");
        List<Integer> transform = new ArrayList<>();
        boolean flag = false;
        int maxTransform = transformationMap.keySet()
            .stream()
            .mapToInt(Integer::intValue)
            .max()
            .orElseThrow(() -> new IllegalStateException("Пустая"));
        do {
            try {

                String[] str = readerLine.read().split(" ");
                for (String s : str) {
                    if (!s.isEmpty()) {
                        if (Integer.parseInt(s) > maxTransform || Integer.parseInt(s) <= 0) {
                            transform = null;
                        }
                        if (transform != null) {
                            transform.add(Integer.valueOf(s));
                        }
                    }
                }
                if (!transform.isEmpty()) {
                    flag = true;
                }

            } catch (NumberFormatException e) {
                consolePrinter.print("Введите валидные трансформации: ");
            }

        } while (!flag);
        return transform;
    }

    public Render inRender() {
        consolePrinter.println("""
            Режимы:
            1 - однопоточный
            2 - многопоточный
            0 - тест сравнения скоростей""");
        consolePrinter.print(
            "Введите какой режим вам нужен: ");
        int render = 0;
        boolean flag = false;
        int maxRender = renderMap.keySet()
            .stream()
            .mapToInt(Integer::intValue)
            .max()
            .orElseThrow(() -> new IllegalStateException("Мапа пустая"));
        do {
            try {

                String str = readerLine.read();

                if (Integer.parseInt(str) <= maxRender && Integer.parseInt(str) >= 0) {
                    render = Integer.parseInt(str);
                    flag = true;
                } else {
                    consolePrinter.print("Такой режим отсутствует. Введите повторно:");
                }

            } catch (NumberFormatException e) {
                consolePrinter.print("Введите валидный режим: ");
            }

        } while (!flag);
        if (render == 0) {
            return null;
        }
        return renderMap.get(render);
    }

    public Blur inBlur() {
        consolePrinter.println("Размытия:\n"
            + "1 - размытие черных блоков\n"
            + "2 - размытие по Гауссу\n"
            + "0 - нет размытия");
        consolePrinter.print(
            "Введите какое размытие вам нужно: ");
        int blur = -1;
        boolean flag = false;
        int maxBlur = blurMap.keySet()
            .stream()
            .mapToInt(Integer::intValue)
            .max()
            .orElseThrow(() -> new IllegalStateException("Map is empty"));
        do {
            try {
                String str = readerLine.read();
                if (Integer.parseInt(str) <= maxBlur && Integer.parseInt(str) >= 0) {
                    blur = Integer.parseInt(str);
                    flag = true;
                } else {
                    consolePrinter.print("Размытие не входит в диапазон. Введите повторно:");
                }

            } catch (NumberFormatException e) {
                consolePrinter.print("Введите валидный номер размытия: ");
            }

        } while (!flag);
        if (blur == 0) {
            return null;
        }
        return blurMap.get(blur);
    }

}
