package backend.academy.my_project.command;

/* интерфейс для алгоритмов поиска
 * init(List<List<Integer>> maze, Dot start, Dot end) - инициализация
 * selectShortestPath() - вставка самого короткого пути и возврат матрицы с этим путем
 * getPrice() - получение цены
 * getValidNeighbours(Dot d, List<List<Integer>> maze) -возврат валидных соседей вершины
 * dotIsValid(Dot d, List<List<Integer>> maze) - проверка валидных вершин
 * copyMazeWithRoute() - копирование лабиринта   и возврат уже с маршрутом*/

import backend.academy.my_project.utilits.ColorsBlocks;
import backend.academy.my_project.utilits.Dot;
import backend.academy.my_project.utilits.Maze;
import backend.academy.my_project.utilits.MazeUtils;
import java.util.ArrayList;
import java.util.List;

public interface CommandFinder {

    Maze init(Maze maze, Dot start, Dot end);

    Maze selectShortestPath(List<Dot> route, Maze grid);

    static List<Dot> getValidNeighboursFinder(Dot d, Maze maze) {
        return MazeUtils.getValidNeighbours(d, maze, ColorsBlocks.WHITE_BLOCK.code());
    }

    static boolean dotIsValid(Dot d, Maze maze) {
        return MazeUtils.dotIsValidEquals(d, maze);
    }


    default Maze copyMazeWithRoute(Maze maze, List<Dot> route, int mark) {
        List<List<Integer>> out = new ArrayList<>(maze.grid().size());
        for (List<Integer> row : maze.grid()) {
            out.add(new ArrayList<>(row));
        }
        for (Dot d : route) {
            if (out.get(d.y()).get(d.x()) == 1) {
                out.get(d.y()).set(d.x(), mark);
            }
        }
        return new Maze(out);
    }

}
