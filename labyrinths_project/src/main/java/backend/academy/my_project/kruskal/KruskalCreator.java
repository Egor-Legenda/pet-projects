package backend.academy.my_project.kruskal;

import backend.academy.my_project.command.CommandCreator;
import backend.academy.my_project.utilits.ColorsBlocks;
import backend.academy.my_project.utilits.Dot;
import backend.academy.my_project.utilits.Maze;
import backend.academy.my_project.utilits.MazeUtils;
import backend.academy.my_project.utilits.Variables;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;

public class KruskalCreator implements CommandCreator {
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();


    @Override
    public Maze init(Dot start, Dot end, int height, int width) {
        Maze maze = MazeUtils.createEmptyMaze(height, width);
        List<List<Integer>> grid = maze.grid();
        List<Edge> edges = generateEdges(maze, width, height);
        Collections.shuffle(edges);
        UnionFind unionFind = new UnionFind(height * width);

        for (Edge edge : edges) {
            int startCell = edge.start;
            int endCell = edge.end;

            if (unionFind.find(startCell) != unionFind.find(endCell)) {
                unionFind.union(startCell, endCell);
                Dot startDot = toDot(startCell, maze);
                Dot endDot = toDot(endCell, maze);
                grid.get(startDot.y()).set(startDot.x(), getRandomNumber());
                grid.get(endDot.y()).set(endDot.x(), ColorsBlocks.WHITE_BLOCK.code());
                int midX = (startDot.x() + endDot.x()) / 2;
                int midY = (startDot.y() + endDot.y()) / 2;

                if (midY >= 0 && midY < height && midX >= 0 && midX < width) {
                    grid.get(midY).set(midX, ColorsBlocks.WHITE_BLOCK.code());
                }
            }
        }
        maze.grid(grid);
        return maze;
    }

    public List<Edge> generateEdges(Maze maze, int width, int height) {
        List<Edge> edges = new ArrayList<>();

        for (int y = 0; y < height; y += 2) {
            for (int x = 0; x < width; x += 2) {
                int cellIndex = toIndex(x, y, maze);
                if (x + 2 < width) {
                    edges.add(new Edge(cellIndex, toIndex(x + 2, y, maze)));
                } else if (x + 1 < width) {
                    edges.add(new Edge(cellIndex, toIndex(x + 1, y, maze)));
                }

                if (y + 2 < height) {
                    edges.add(new Edge(cellIndex, toIndex(x, y + 2, maze)));
                } else if (y + 1 < height) {
                    edges.add(new Edge(cellIndex, toIndex(x, y + 1, maze)));
                }
            }
        }
        return edges;
    }

    private int toIndex(int x, int y, Maze maze) {
        return y * maze.getWidth() + x;
    }

    private Dot toDot(int index, Maze maze) {
        int x = index % maze.getWidth();
        int y = index / maze.getWidth();
        return new Dot(x, y);
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

    public static class UnionFind {
        private final int[] parent;

        public UnionFind(int size) {
            parent = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;
            }
        }

        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            if (rootX != rootY) {
                parent[rootY] = rootX;
            }
        }
    }

    @AllArgsConstructor
    public static class Edge {
        int start;
        int end;
    }
}
