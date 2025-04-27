package backend.academy.my_project.tests.utilitsTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import backend.academy.my_project.utilits.ColorsBlocks;
import backend.academy.my_project.utilits.Dot;
import backend.academy.my_project.utilits.Maze;
import backend.academy.my_project.utilits.MazeUtils;
import backend.academy.my_project.utilits.Variables;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MazeUtilsTest {

    private Maze maze;

    @BeforeEach
    public void setUp() {
        maze = MazeUtils.createEmptyMaze(3, 3);
    }

    @Test
    @DisplayName("Should create an empty maze with all cells as walls")
    public void testCreateEmptyMaze() {
        Maze emptyMaze = MazeUtils.createEmptyMaze(3, 3);
        assertThat(emptyMaze.getWidth()).isEqualTo(3);
        assertThat(emptyMaze.getHeight()).isEqualTo(3);
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                assertThat(emptyMaze.getNumber(x, y)).isEqualTo(ColorsBlocks.BLACK_BLOCK.code());
            }
        }
    }

    @Test
    @DisplayName("Should return valid neighbors where cell equals WALL when number is 0")
    public void testGetValidNeighborsEquals() {
        Dot dot = new Dot(1, 1);
        List<Dot> neighbors = MazeUtils.getValidNeighbours(dot, maze, 0);
        assertThat(neighbors).containsExactlyInAnyOrder(
            new Dot(0, 1), new Dot(2, 1), new Dot(1, 0), new Dot(1, 2)
        );
    }

    @Test
    @DisplayName("Should return true if dot is within bounds and cell equals WALL")
    public void testDotIsValidEquals() {
        Dot dot = new Dot(1, 1);
        assertTrue(MazeUtils.dotIsValidEquals(dot, maze));
    }

    @Test
    @DisplayName("Should return true if dot is within bounds and cell is not WALL")
    public void testDotIsValidNotEquals() {
        Dot dot = new Dot(0, 1);
        maze.setNumber(0, 1, 0);
        assertFalse(MazeUtils.dotIsValidNotEquals(dot, maze));
    }
}

