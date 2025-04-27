package backend.academy.my_project.tests.kruskalTests;

import backend.academy.my_project.kruskal.KruskalCreator;
import backend.academy.my_project.utilits.Dot;
import backend.academy.my_project.utilits.Maze;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KruskalCreatorTest {

    private KruskalCreator kruskalCreator;
    private final int height = 11; // Используем нечётные размеры для корректного построения
    private final int width = 11;
    private Dot start;
    private Dot end;
    private Maze maze;

    @BeforeEach
    void setUp() {
        kruskalCreator = new KruskalCreator();
        start = new Dot(1, 1);
        end = new Dot(9, 9);
        maze = kruskalCreator.init(start, end, height, width);
    }

    @Test
    @DisplayName("Test maze dimensions: checks if maze dimensions match expected values")
    void testMazeDimensions() {
        List<List<Integer>> grid = maze.grid();
        assertEquals(height, grid.size(), "Maze height should match the given height.");
        for (List<Integer> row : grid) {
            assertEquals(width, row.size(), "Each row in the maze should match the given width.");
        }
    }

    @Test
    @DisplayName("Test start and end positions: verifies start and end points are paths")
    void testStartAndEndPositions() {
        List<List<Integer>> grid = maze.grid();
        assertEquals(0, grid.get(start.y()).get(start.x()), "Start position should be a path.");
        assertEquals(0, grid.get(end.y()).get(end.x()), "End position should be a path.");
    }

    @Test
    @DisplayName("Test walls and paths: checks maze contains both walls and paths")
    void testWallsAndPaths() {
        List<List<Integer>> grid = maze.grid();
        int wallCount = 0;
        int pathCount = 0;

        for (List<Integer> row : grid) {
            for (Integer cell : row) {
                if (cell == 0) {
                    wallCount++;
                } else if (cell == 1 || cell == 2 || cell == 3) {
                    pathCount++;
                }
            }
        }
        assertTrue(pathCount > 0, "There should be at least one path.");
        assertTrue(wallCount > 0, "There should be at least one wall.");
    }

    @Test
    @DisplayName("Test random number generation: verifies random number is within expected range")
    void testRandomNumberGeneration() {
        int randomValue = kruskalCreator.getRandomNumber();
        assertTrue(randomValue == 1 || randomValue == 2 || randomValue == 3,
            "Random number should be 1, 2, or 3.");
    }


    @Test
    @DisplayName("Test union-find functionality: verifies union-find connects and separates correctly")
    void testUnionFindFunctionality() {
        KruskalCreator.UnionFind unionFind = new KruskalCreator.UnionFind(10);
        unionFind.union(1, 2);
        unionFind.union(2, 3);
        assertEquals(unionFind.find(1), unionFind.find(3), "1 and 3 should be connected.");
        assertNotEquals(unionFind.find(1), unionFind.find(4), "1 and 4 should not be connected.");
    }
}
