package backend.academy.my_project.dfs;

/* Создание лабиринта алгоритмом Dfs */

import backend.academy.my_project.command.CommandCreator;
import backend.academy.my_project.utilits.ColorsBlocks;
import backend.academy.my_project.utilits.Dot;
import backend.academy.my_project.utilits.Maze;
import backend.academy.my_project.utilits.MazeUtils;
import backend.academy.my_project.utilits.Variables;
import java.security.SecureRandom;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class DfsCreator implements CommandCreator {
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    @Override
    public Maze init(Dot start, Dot end, int height, int width) {
        Maze maze = MazeUtils.createEmptyMaze(height, width);
        return dfs(start, end, maze);
    }

    public Maze dfs(Dot start, Dot end, Maze maze) {
        Deque<Dot> dots = new ArrayDeque<>();
        dots.push(start);
        maze.setNumber(start.x(), start.y(), getRandomNumber());

        while (!dots.isEmpty()) {
            Dot current = dots.pop();

            if (current.x() == end.x() && current.y() == end.y()) {
                break;
            }

            List<Dot> neighbours = CommandCreator.getValidNeighboursCreator(current, maze);
            while (!neighbours.isEmpty()) {
                Dot next = neighbours.remove(SECURE_RANDOM.nextInt(neighbours.size()));
                if (countPathNeighbours(next, maze) < 2) {
                    maze.setNumber(next.x(), next.y(), getRandomNumber());
                    dots.push(next);
                }
            }
        }
        return maze;
    }


    private int countPathNeighbours(Dot d, Maze maze) {
        int count = 0;
        Dot[] neighbours = {
            new Dot(d.x() + 1, d.y()),
            new Dot(d.x() - 1, d.y()),
            new Dot(d.x(), d.y() + 1),
            new Dot(d.x(), d.y() - 1)
        };

        for (Dot neighbour : neighbours) {
            if (0 <= neighbour.x() && neighbour.x() < maze.getWidth()
                && 0 <= neighbour.y() && neighbour.y() < maze.getHeight()
                && maze.getNumber(neighbour.x(), neighbour.y()) != 0) {
                count++;
            }
        }
        return count;
    }

    @Override
    public int getRandomNumber() {

        int randomValue = SECURE_RANDOM.nextInt(Variables.BOUND.variable());

        if (randomValue < Variables.THRESHOLD_ONE.variable()) {
            return ColorsBlocks.WHITE_BLOCK.code();
        } else if (randomValue < Variables.THRESHOLD_TWO.variable()) {
            return ColorsBlocks.GREEN_BLOCK.code();
        } else {
            return ColorsBlocks.CYAN_BLOCK.code();
        }
    }
}
