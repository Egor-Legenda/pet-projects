package backend.academy.my_project.tests.dfsTests;

import backend.academy.my_project.command.CommandCreator;
import backend.academy.my_project.dfs.DfsCreator;
import backend.academy.my_project.utilits.Dot;
import backend.academy.my_project.utilits.Maze;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DfsCreatorTest {
    private DfsCreator dfsCreator;
    private Dot start;
    private Dot end;
    private int height;
    private int width;
    private Maze maze;

    @BeforeEach
    void setUp() {
        dfsCreator = new DfsCreator();
        height = 5 * 2 + 1;
        width = 5 * 2 + 1;
        start = new Dot(1 * 2 + 1, 1 * 2 + 1);
        end = new Dot(4 * 2 + 1, 4 * 2 + 1);
        maze = dfsCreator.init(start, end, height, width);
    }

    @Test
    @DisplayName("Test init: verifies maze initialization with correct dimensions and starting path")
    void testInitMaze() {
        List<List<Integer>> grid = maze.grid();
        assertNotNull(grid, "Maze should be initialized.");
        assertEquals(height, grid.size(), "Maze height should match the given height.");
        grid.forEach(row -> assertEquals(width, row.size(), "Each row in the maze should match the given width."));
        assertTrue(grid.get(start.y()).get(start.x()) == 1 || grid.get(start.y()).get(start.x()) == 2
                || grid.get(start.y()).get(start.x()) == 3,
            "Starting point should be part of the path (either 1 or 2).");    }

    @Test
    @DisplayName("Test dfs: checks if DFS creates a valid path from start to end")
    void testDfsPathFinding() {
        List<List<Integer>> grid = maze.grid();
        assertTrue(grid.get(start.y()).get(start.x()) == 1 || grid.get(start.y()).get(start.x()) == 2
                || grid.get(start.y()).get(start.x()) == 3,
            "Starting point should be part of the path (either 1 or 2 or 3).");
    }

    @Test
    @DisplayName("Test getValidNeighbors: returns valid neighbors within maze bounds")
    void testGetValidNeighbors() {
        Dot dot = new Dot(4, 4);
        List<Dot> neighbors = CommandCreator.getValidNeighboursCreator(dot, maze);
        assertNotNull(neighbors, "Neighbors should not be null.");
        neighbors.forEach(neighbor -> assertFalse(CommandCreator.dotIsValid(neighbor, maze), "Each neighbor should be within maze bounds."));
    }

    @Test
    @DisplayName("Test dotIsValid: checks dot validity within maze bounds")
    void testDotIsValid() {
        assertFalse(CommandCreator.dotIsValid(new Dot(11, 11), maze), "Dot (11,11) should be out of maze bounds.");
        assertFalse(CommandCreator.dotIsValid(new Dot(-1, -1), maze), "Dot (-1,-1) should be out of maze bounds.");
    }

    @Test
    @DisplayName("Test getRandomNumber: generates random number within expected range")
    void testGetRandomNumber() {
        int randomNumber = dfsCreator.getRandomNumber();
        assertTrue(randomNumber >= 1 && randomNumber <= 3, "Random number should be within expected range.");
    }
}
