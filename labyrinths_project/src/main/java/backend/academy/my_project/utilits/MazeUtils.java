package backend.academy.my_project.utilits;

import java.util.ArrayList;
import java.util.List;

public class MazeUtils {

    public static List<Dot> getValidNeighbours(Dot d, Maze maze, int number) {
        List<Dot> neighbors = new ArrayList<>();
        Dot[] potentialNeighbors = {
            new Dot(d.x() + 1, d.y()),
            new Dot(d.x() - 1, d.y()),
            new Dot(d.x(), d.y() + 1),
            new Dot(d.x(), d.y() - 1)
        };

        for (Dot neighbor : potentialNeighbors) {
            if (number == 0) {

                if (dotIsValidEquals(neighbor, maze)) {
                    neighbors.add(neighbor);
                }
            } else if (number == 1) {
                if (dotIsValidNotEquals(neighbor, maze)) {
                    neighbors.add(neighbor);
                }
            }
        }
        return neighbors;
    }

    public static boolean dotIsValidEquals(Dot d, Maze maze) {
        return (d.x() >= 0 && d.x() < maze.getWidth()
            && d.y() >= 0 && d.y() < maze.getHeight() && maze.getNumber(d.x(), d.y())
            == ColorsBlocks.BLACK_BLOCK.code());
    }

    public static boolean dotIsValidNotEquals(Dot d, Maze maze) {
        return (d.x() >= 0 && d.x() < maze.getWidth()
            && d.y() >= 0 && d.y() < maze.getHeight() && maze.getNumber(d.x(), d.y())
            != ColorsBlocks.BLACK_BLOCK.code());
    }

    public static Maze createEmptyMaze(int height, int width) {
        List<List<Integer>> grid = new ArrayList<>(height);
        for (int i = 0; i < height; i++) {
            List<Integer> row = new ArrayList<>(width);
            for (int j = 0; j < width; j++) {
                row.add(ColorsBlocks.BLACK_BLOCK.code());
            }
            grid.add(row);
        }
        return new Maze(grid);
    }

    private MazeUtils() {
    }
}
