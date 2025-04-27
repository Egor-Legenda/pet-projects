package backend.academy.my_project.tests.bfsTests;

import backend.academy.my_project.bfs.BfsFinder;
import backend.academy.my_project.utilits.Dot;
import backend.academy.my_project.utilits.Maze;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BfsFinderTest {

    private BfsFinder bfsFinder;
    private Maze maze;
    private Dot start;
    private Dot end;

    @BeforeEach
    void setUp() {
        bfsFinder = new BfsFinder();
        List<List<Integer>> grid = new ArrayList<>();
        grid.add(List.of(0, 1, 1, 1, 1));
        grid.add(List.of(1, 1, 0, 0, 1));
        grid.add(List.of(1, 1, 1, 0, 1));
        grid.add(List.of(1, 0, 1, 1, 1));
        grid.add(List.of(1, 1, 1, 1, 1));
        maze = new Maze(grid);
        start = new Dot(1, 1);
        end = new Dot(4, 4);
        bfsFinder.init(maze, start, end);
    }

    @Test
    @DisplayName("Test BFS path finding from start to end")
    void testBfsPathFinding() {
        List<Dot> path = bfsFinder.bfs(start, end, maze);
        assertNotNull(path, "Path should not be null");
        assertFalse(path.isEmpty(), "Path should not be empty");
        assertEquals(end, path.get(path.size() - 1), "Path should end at the destination point");

        for (Dot dot : path) {
            assertTrue(maze.getNumber(dot.x(), dot.y()) != 0, "Path should only go through passable cells");
        }
    }

    @Test
    @DisplayName("Test for findShortestPath returns a valid Maze object")
    void testFindShortestPath() {
        List<Dot> route = bfsFinder.bfs(start, end, maze);
        Maze shortestPathMaze = bfsFinder.selectShortestPath(route, maze);
        assertNotNull(shortestPathMaze, "Shortest path maze should not be null");
        assertEquals(maze.getHeight(), shortestPathMaze.grid().size(), "Maze and path maze should have the same size");
        boolean pathExists = shortestPathMaze.grid().stream().anyMatch(row -> row.contains(BfsFinder.BLUE_BLOCK));
        assertTrue(pathExists, "Shortest path maze should contain BLUE_BLOCK cells on the route");
    }

    @Test
    @DisplayName("Test isVisitedNeighbor returns correct result")
    void testIsVisitedNeighbor() {
        Dot visitedDot = new Dot(2, 2);
        Dot neighborDot = new Dot(2, 2);
        Set<Dot> visited = new HashSet<>();
        visited.add(visitedDot);

        assertTrue(BfsFinder.isVisitedNeighbour(visited, neighborDot), "isVisitedNeighbor should return true for visited neighbor");
        assertFalse(BfsFinder.isVisitedNeighbour(visited, new Dot(0, 0)), "isVisitedNeighbor should return false for unvisited neighbor");
    }

    @Test
    @DisplayName("Test BFS route cost calculation")
    void testReconstructPrice() {
        List<Dot> path = bfsFinder.bfs(start, end, maze);
        double calculatedPrice = bfsFinder.init(maze, start, end).cost();
        assertTrue(calculatedPrice >= 0, "Path price should be non-negative");
    }

    @Test
    @DisplayName("Test route initialization in init method")
    void testRouteInitializationInInitMethod() {
        Maze mazeResult = bfsFinder.init(maze, start, end);
        assertNotNull(mazeResult, "Maze result should be initialized by init method");
    }
}
