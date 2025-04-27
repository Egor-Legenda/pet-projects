package backend.academy.my_project.astar;

/* Класс для поиска самого короткого пути (алгоритм astar)
 */

import backend.academy.my_project.command.CommandFinder;
import backend.academy.my_project.utilits.Dot;
import backend.academy.my_project.utilits.Maze;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class AStarFinder implements CommandFinder {
    private static final double[] COAST = {0, 1, 5, 0, 1, 1};
    public static final Integer BLUE_BLOCK = 4;
    @Getter
    private List<Dot> route;

    @Override
    public Maze init(Maze maze, Dot start, Dot end) {

        route = astar(start, end, maze);
        Maze m = selectShortestPath(route, maze);
        m.cost(reconstructPrice(route, maze));
        return m;

    }

    public List<Dot> astar(Dot start, Dot end, Maze maze) {
        if (start.y() == end.y() && start.x() == end.x()) {
            return Collections.singletonList(start);
        }


        int initialCapacity = maze.getHeight() * maze.getWidth();
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(Node::f));
        Map<Dot, Dot> parent = new HashMap<>(initialCapacity);
        Set<Dot> closedSet = new HashSet<>(initialCapacity);
        Map<Dot, Double> gScore = new HashMap<>(initialCapacity);

        openSet.add(new Node(start, 0, heuristic(start, end)));
        parent.put(start, null);
        gScore.put(start, 0.0);

        while (!openSet.isEmpty()) {
            Node currentNode = openSet.poll();
            Dot current = currentNode.dot();

            if (current.y() == end.y() && current.x() == end.x()) {
                return reconstructPath(parent, current);
            }

            closedSet.add(current);

            List<Dot> neighbours = getValidNeighbours(current, maze);
            for (Dot neighbour : neighbours) {
                if (closedSet.contains(neighbour)) {
                    continue;
                }

                Double currentG = gScore.get(neighbour);
                double tentativeG = gScore.get(current) + distance(neighbour, maze);

                if (currentG == null || tentativeG < currentG) {
                    try {
                        parent.put(neighbour, current);
                    } catch (OutOfMemoryError e) {
                        return null;
                    }
                    gScore.put(neighbour, tentativeG);
                    double f = tentativeG + heuristic(neighbour, end);
                    openSet.add(new Node(neighbour, tentativeG, f));
                }
            }
        }

        return Collections.emptyList();
    }

    public double heuristic(Dot a, Dot b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }

    public double distance(Dot b, Maze grid) {
        return COAST[grid.getNumber(b.x(), b.y())];
    }

    private List<Dot> reconstructPath(Map<Dot, Dot> parent, Dot target) {
        List<Dot> path = new ArrayList<>(parent.size());
        Dot at = target;
        while (at != null) {
            path.add(at);
            at = parent.get(at);
        }
        Collections.reverse(path);
        return path;
    }

    private Double reconstructPrice(List<Dot> dots, Maze maze) {
        Double price = (double) 0;
        for (Dot at : dots) {
            price += COAST[maze.getNumber(at.x(), at.y())];
        }
        return price;
    }

    public List<Dot> getValidNeighbours(Dot d, Maze maze) {
        return CommandFinder.getValidNeighboursFinder(d, maze);
    }

    @Override
    public Maze selectShortestPath(List<Dot> route, Maze maze) {
        return copyMazeWithRoute(maze, route, BLUE_BLOCK);
    }

    @Getter @Setter @AllArgsConstructor
    private static class Node {
        private final Dot dot;
        private final double g;
        private final double f;


    }
}
