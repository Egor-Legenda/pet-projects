package backend.academy.my_project.tests.utilitsTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import backend.academy.my_project.utilits.Maze;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MazeTest {

    private Maze maze;

    @BeforeEach
    public void setUp() {
        List<List<Integer>> grid = Arrays.asList(
            Arrays.asList(0, 1, 0),
            Arrays.asList(1, 0, 1),
            Arrays.asList(0, 1, 0)
        );
        maze = new Maze(grid);
    }

    @Test
    @DisplayName("Should get the correct number at given coordinates")
    public void testGetNumber() {
        assertThat(maze.getNumber(0, 0)).isEqualTo(0);
        assertThat(maze.getNumber(1, 1)).isEqualTo(0);
        assertThat(maze.getNumber(2, 1)).isEqualTo(1);
    }

    @Test
    @DisplayName("Should set the correct number at given coordinates")
    public void testSetNumber() {
        maze.setNumber(0, 0, 1);
        assertThat(maze.getNumber(0, 0)).isEqualTo(1);
        maze.setNumber(2, 2, 1);
        assertThat(maze.getNumber(2, 2)).isEqualTo(1);
    }

    @Test
    @DisplayName("Should return correct width of the grid")
    public void testGetWidth() {
        assertThat(maze.getWidth()).isEqualTo(3);
    }

    @Test
    @DisplayName("Should return correct height of the grid")
    public void testGetHeight() {
        assertThat(maze.getHeight()).isEqualTo(3);
    }

    @Test
    @DisplayName("Should throw exception for out of bounds coordinates")
    public void testOutOfBounds() {
        assertThrows(IndexOutOfBoundsException.class, () -> maze.getNumber(3, 3));
        assertThrows(IndexOutOfBoundsException.class, () -> maze.setNumber(3, 3, 1));
    }
}
