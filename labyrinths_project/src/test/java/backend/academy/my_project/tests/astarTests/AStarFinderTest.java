package backend.academy.my_project.tests.astarTests;


import backend.academy.my_project.astar.AStarFinder;
import backend.academy.my_project.utilits.Dot;
import backend.academy.my_project.utilits.Maze;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AStarFinderTest {

    private AStarFinder aStarFinder;
    private Maze maze;
    private Dot start;
    private Dot end;

    @BeforeEach
    void setUp() {
        aStarFinder = new AStarFinder();
        List<List<Integer>> grid = new ArrayList<>();
        grid.add(List.of(1, 1, 1, 1, 1));
        grid.add(List.of(1, 3, 0, 0, 1));
        grid.add(List.of(1, 1, 1, 0, 1));
        grid.add(List.of(1, 0, 1, 1, 1));
        grid.add(List.of(1, 1, 1, 1, 1));
        maze = new Maze(grid);
        start = new Dot(0, 0);
        end = new Dot(4, 4);
    }

    @Test
    @DisplayName("Test init method with maze, start, and end")
    void testInitMethod() {
        Maze resultMaze = aStarFinder.init(maze, start, end);
        assertNotNull(resultMaze, "Maze result should not be null");
        assertNotNull(resultMaze.cost(), "Maze result cost should not be null");
        assertNotNull(aStarFinder.route(), "Route should be initialized by init method");
    }

    @Test
    @DisplayName("Test A* path finding")
    void testAStarPathFinding() {
        List<Dot> path = aStarFinder.astar(start, end, maze);
        assertNotNull(path, "Path should not be null");
        assertFalse(path.isEmpty(), "Path should not be empty");
        assertEquals(end, path.get(path.size() - 1), "Path should end at the destination point");

        for (Dot dot : path) {
            assertTrue(maze.getNumber(dot.x(), dot.y()) == 1 || maze.getNumber(dot.x(), dot.y()) == 3,
                "Path should only go through passable cells");
        }
    }

    @Test
    @DisplayName("Test Manhattan heuristic")
    void testHeuristic() {
        double heuristicValue = aStarFinder.heuristic(start, end);
        assertEquals(8, heuristicValue, "Heuristic should return correct Manhattan distance");
    }

    @Test
    @DisplayName("Test cell cost calculation")
    void testDistance() {
        Dot passableDot = new Dot(0, 1);
        double distanceValue = aStarFinder.distance(passableDot, maze);
        assertEquals(1, distanceValue, "Distance should match the cost for cell type 1");

        Dot impassableDot = new Dot(1, 1);
        assertEquals(0, aStarFinder.distance(impassableDot, maze), "Distance to impassable cells should be 0");
    }

    @Test
    @DisplayName("Test shortest path with BLUE_BLOCK")
    void testFindShortestPath() {
        List<Dot> path = aStarFinder.astar(start, end, maze);
        Maze shortestPathMaze = aStarFinder.selectShortestPath(path, maze);
        assertNotNull(shortestPathMaze, "Shortest path maze should not be null");
        assertEquals(maze.getHeight(), shortestPathMaze.grid().size(), "Maze and path maze should have the same size");
        boolean pathExists = shortestPathMaze.grid().stream().anyMatch(row -> row.contains(AStarFinder.BLUE_BLOCK));
        assertTrue(pathExists, "Shortest path maze should contain BLUE_BLOCK cells on the route");
    }

    @Test
    @DisplayName("Test route cost calculation")
    void testGetRouteCost() {
        Maze resultMaze = aStarFinder.init(maze, start, end);
        Double calculatedCost = resultMaze.cost();
        assertNotNull(calculatedCost, "Route cost should be calculated and not null");
        assertTrue(calculatedCost >= 0, "Path cost should be non-negative");
    }
}
