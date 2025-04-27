package backend.academy.my_project.command;

/* Интерфейс для всех алгоритмов, которые создают лабиринт
 * getMaze() - получение уже готового лабиринта
 * init(Dot start, Dot end, int height, int width) -получение основных данных для создания алгоритма
 * getRandomNumber() - выбор рандомного препятствия или прохода */

import backend.academy.my_project.utilits.ColorsBlocks;
import backend.academy.my_project.utilits.Dot;
import backend.academy.my_project.utilits.Maze;
import backend.academy.my_project.utilits.MazeUtils;
import java.util.List;

public interface CommandCreator {

    Maze init(Dot start, Dot end, int height, int width);

    int getRandomNumber();

    static List<Dot> getValidNeighboursCreator(Dot d, Maze maze) {
        return MazeUtils.getValidNeighbours(d, maze, ColorsBlocks.BLACK_BLOCK.code());
    }

    static boolean dotIsValid(Dot d, Maze maze) {
        return MazeUtils.dotIsValidNotEquals(d, maze);
    }
}
