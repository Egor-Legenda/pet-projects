package backend.academy.my_project.mazeGeneration;

/* Основной класс
   start() - запуск игры
   generation() - генерация лабиринта, а затем и его решения
   printPrice() - публикация цены прохода до точки
   printHint() - вывод подсказки
   drawMaze(List<List<Integer>> m) - отрисовка лабиринта
   selectAlgorithmCreator(HashMap<String, CommandCreator> commands) - выбор алгоритма отрисовки
   isValidAlgorithmCreator(String a, HashMap<String, CommandCreator> commands) - проверка на валидность
   validateAlgorithmCreator(HashMap<String, CommandCreator> commands) - если такого нет выбор рандомного
   Такие же методы для поиска пути
   putCommand() - добавление команд и создания и поиска
   configureDepth() -выбор глубины лабиринта (yMax)
   configureWidth() - выбор ширины лабиринта
   configureDot(String str) - выбор точки конца и начала
  */

import backend.academy.my_project.astar.AStarFinder;
import backend.academy.my_project.bfs.BfsFinder;
import backend.academy.my_project.command.CommandCreator;
import backend.academy.my_project.command.CommandFinder;
import backend.academy.my_project.dfs.DfsCreator;
import backend.academy.my_project.kruskal.KruskalCreator;
import backend.academy.my_project.print.ConsolePrinter;
import backend.academy.my_project.reader.ReaderLine;
import backend.academy.my_project.utilits.ColorsBlocks;
import backend.academy.my_project.utilits.Dot;
import backend.academy.my_project.utilits.Maze;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class MazeGeneration {

    @Setter @Getter
    private Maze maze;

    private HashMap<String, CommandCreator> algorithmsCreator;
    private HashMap<String, CommandFinder> algorithmsFinder;
    @Setter
    private ConsolePrinter consolePrinter;
    @Setter
    private ReaderLine readerLine;
    private CommandCreator creator;
    private CommandFinder finder;
    private Dot end;
    private Dot start;
    private int depth;
    private int width;
    private static SecureRandom secureRandom = new SecureRandom();
    private static final Integer MAX_VALUE = 50;

    private static final List<String> COLORS = new ArrayList<>(Arrays.asList(
        "\u2B1B",
        "\u2B1C",
        "\uD83D\uDFE2",
        "\uD83D\uDFE1",
        "\uD83D\uDFE6",
        "\uD83D\uDFE5",
        "\uD83D\uDFE8",
        "\uD83D\uDFE7",
        "\uD83D\uDFEB",
        "\uD83D\uDFE3"
    ));


    public void start() {
        String str = "+";
        while (!"-".equals(str)) {
            generation();
            do {
                consolePrinter.print("Хотите продолжить игру(+, -)? ");
                str = readerLine.read();
            } while (!"-".equals(str) && !"+".equals(str));
        }
    }

    public void generation() {
        if (algorithmsFinder == null || algorithmsCreator == null
            || algorithmsCreator.isEmpty() || algorithmsFinder.isEmpty()) {
            consolePrinter = new ConsolePrinter();
            readerLine = new ReaderLine();
            putCommand();
        }
        depth = configureDepth();
        width = configureWidth();
        start = configureDot("начала");
        end = configureDot("конец");
        creator = selectAlgorithmCreator(algorithmsCreator);
        maze = creator.init(start, end, depth, width);
        drawMaze(maze.grid());
        printHint();
        finder = selectAlgorithmFinder(algorithmsFinder);
        maze = finder.init(maze, start, end);
        drawMaze(maze.grid());
        printPrice(maze.cost());
    }

    public void printPrice(Double price) {
        if (price >= 0) {
            consolePrinter.println("Данный маршрут вам обойдется в " + price);
        } else {
            consolePrinter.println("За эту поездку вам заплатят " + price);
        }
    }

    public void printHint() {
        consolePrinter.print(ColorsBlocks.getSymbolByCode(ColorsBlocks.BLACK_BLOCK.code())
            + "- используется для тупиков ");
        consolePrinter.println(ColorsBlocks.getSymbolByCode(ColorsBlocks.WHITE_BLOCK.code())
            + "- используется для проходов(-1 монета) ");
        consolePrinter.print(ColorsBlocks.getSymbolByCode(ColorsBlocks.GREEN_BLOCK.code())
            + "- используется для создания болот(-5 монет) ");
        consolePrinter.print(ColorsBlocks.getSymbolByCode(ColorsBlocks.CYAN_BLOCK.code())
            + "- проезд бесплатный(0 монет) ");
        consolePrinter.println(ColorsBlocks.getSymbolByCode(ColorsBlocks.BLUE_BLOCK.code())
            + "- прокладывают маршрут ");
    }

    public void drawMaze(List<List<Integer>> m) {
        m.get(end.y()).set(end.x(), ColorsBlocks.RED_BLOCK.code());
        m.get(start.y()).set(start.x(), ColorsBlocks.RED_BLOCK.code());

        for (List<Integer> row : m) {
            for (Integer cell : row) {
                consolePrinter.print(String.valueOf(ColorsBlocks.getSymbolByCode(cell)));
            }
            consolePrinter.println("");
        }
    }

    public CommandCreator selectAlgorithmCreator(HashMap<String, CommandCreator> commands) {
        consolePrinter.print("Выберите алгоритм генерации из " + commands.keySet() + ": ");
        String algorithm = isValidAlgorithmCreator(readerLine.read(), commands);

        return commands.get(algorithm);
    }

    public String isValidAlgorithmCreator(String a, HashMap<String, CommandCreator> commands) {
        CommandCreator command = commands.get(a);
        if (command == null) {
            return validateAlgorithmCreator(commands);
        } else {
            return a.toLowerCase();
        }
    }

    public String validateAlgorithmCreator(HashMap<String, CommandCreator> commands) {
        String str = (String) commands.keySet().toArray()[secureRandom.nextInt(commands.size())];
        consolePrinter.println("Был выбран, как алгоритм создания " + str);
        return str;
    }

    public CommandFinder selectAlgorithmFinder(HashMap<String, CommandFinder> commands) {
        consolePrinter.print("Выберите алгоритм поиска из " + commands.keySet() + ": ");
        String algorithm = isValidAlgorithmFinder(readerLine.read(), commands);

        return commands.get(algorithm);
    }

    public String isValidAlgorithmFinder(String a, HashMap<String, CommandFinder> commands) {
        CommandFinder command = commands.get(a);
        if (command == null) {
            return validateAlgorithmFinder(commands);
        } else {
            return a.toLowerCase();
        }
    }

    public String validateAlgorithmFinder(HashMap<String, CommandFinder> commands) {
        String str = (String) commands.keySet().toArray()[secureRandom.nextInt(commands.size())];
        consolePrinter.println("Был выбран, как алгоритм поиска " + str);
        return str;
    }

    public void putCommand() {
        algorithmsCreator = new HashMap<>();
        algorithmsCreator.put("dfs", new DfsCreator());
        algorithmsCreator.put("kruskal", new KruskalCreator());

        algorithmsFinder = new HashMap<>();
        algorithmsFinder.put("bfs", new BfsFinder());
        algorithmsFinder.put("astar", new AStarFinder());

    }

    public int configureDepth() {
        consolePrinter.print("Введите глубину лабиринта(<=50): ");
        int depthIn = 0;
        while (depthIn == 0) {
            try {
                depthIn = Integer.parseInt(readerLine.read());

            } catch (NumberFormatException e) {
                depthIn = 0;
            }
            if (depthIn <= 0 || depthIn > MAX_VALUE) {
                depthIn = 0;
                consolePrinter.print("Введите повторно(глубина должна быть >0): ");
            }
        }
        return depthIn;
    }

    public int configureWidth() {
        consolePrinter.print("Введите ширину лабиринта(<=50): ");
        int widthIn = 0;
        while (widthIn == 0) {
            try {
                widthIn = Integer.parseInt(readerLine.read());

            } catch (NumberFormatException e) {
                widthIn = 0;
            }
            if (widthIn <= 0 || widthIn > MAX_VALUE) {
                widthIn = 0;
                consolePrinter.print("Введите повторно(ширину должна быть >0): ");
            }
        }
        return widthIn;
    }

    public Dot configureDot(String str) {
        consolePrinter.print("Введите координаты точки " + str + " начиная с точки 1 1 через пробел (x y):");
        Dot dot = null;

        while (dot == null) {
            try {
                String[] input = readerLine.read().trim().split("\\s+");

                if (input.length == 2) {
                    int x = Integer.parseInt(input[0].trim());
                    int y = Integer.parseInt(input[1].trim());

                    if (x > 0 && y > 0 && x <= width && y <= depth) {
                        dot = new Dot((x - 1), (y - 1));
                    } else {
                        consolePrinter.print("Координаты должны быть положительными. Попробуйте снова: ");
                    }
                } else {
                    consolePrinter.print("Введите две координаты через пробел. Попробуйте снова: ");
                }
            } catch (NumberFormatException e) {
                consolePrinter.print("Ошибка формата. Введите координаты повторно: ");
            }
        }

        return dot;
    }

}
