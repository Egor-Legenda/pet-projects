package backend.academy.my_project.bfs;

/*Класс для поиска пути не факт, что самого дешевого(алгоритм BFS)*/

import backend.academy.my_project.command.CommandFinder;
import backend.academy.my_project.utilits.Dot;
import backend.academy.my_project.utilits.Maze;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;


public class BfsFinder implements CommandFinder {
    public static final Integer BLUE_BLOCK = 4;
    private static final double[] COAST = {0, 1, 5, 0, 1, 1};

    @Override
    public Maze init(Maze maze, Dot start, Dot end) {
        List<Dot> route = bfs(start, end, maze);
        Maze m = selectShortestPath(route, maze);
        m.cost(reconstructPrice(route, maze));
        return m;
    }

    public List<Dot> bfs(Dot start, Dot end, Maze maze) {
        if (start.y() == end.y() && start.x() == end.x()) {
            return Collections.singletonList(start);
        }

        Queue<Dot> queue = new LinkedList<>();
        Map<Dot, Dot> parent = new HashMap<>();
        Set<Dot> visited = new HashSet<>();
        queue.add(start);
        visited.add(start);
        parent.put(start, null);

        while (!queue.isEmpty()) {
            Dot current = queue.poll();
            if (current.y() == end.y() && current.x() == end.x()) {
                return reconstructPath(parent, current);
            }

            List<Dot> neighbours = getValidNeighbours(current, maze);
            for (Dot neighbour : neighbours) {
                if (!isVisitedNeighbour(visited, neighbour)) {
                    visited.add(neighbour);
                    parent.put(neighbour, current);
                    queue.add(neighbour);
                }
            }
        }

        return Collections.emptyList();
    }

    private Double reconstructPrice(List<Dot> dots, Maze maze) {
        Double price = (double) 0;
        for (Dot at : dots) {
            price += COAST[maze.getNumber(at.x(), at.y())];
        }
        return price;
    }

    private List<Dot> reconstructPath(Map<Dot, Dot> parent, Dot target) {
        List<Dot> path = new ArrayList<>();
        Dot at = target;
        while (at != null) {
            path.add(at);
            at = parent.get(at);
        }

        Collections.reverse(path);

        return path;
    }

    public static boolean isVisitedNeighbour(Set<Dot> visited, Dot neighbour) {
        for (Dot visit : visited) {
            if (visit.x() == neighbour.x() && visit.y() == neighbour.y()) {
                return true;
            }
        }
        return false;
    }

    public List<Dot> getValidNeighbours(Dot d, Maze maze) {
        return CommandFinder.getValidNeighboursFinder(d, maze);
    }

    @Override
    public Maze selectShortestPath(List<Dot> route, Maze maze) {
        return copyMazeWithRoute(maze, route, BLUE_BLOCK);
    }

}
