package backend.academy.my_project.tests.commandTests;

import backend.academy.my_project.command.CommandFinder;
import backend.academy.my_project.utilits.Dot;
import backend.academy.my_project.utilits.Maze;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CommandFinderTest {

    private CommandFinder commandFinder;
    private Maze maze;

    @BeforeEach
    void setUp() {
        commandFinder = Mockito.mock(CommandFinder.class);
        List<List<Integer>> grid = new ArrayList<>();
        grid.add(List.of(0, 1, 0, 1));
        grid.add(List.of(0, 1, 1, 0));
        grid.add(List.of(1, 0, 1, 1));
        grid.add(List.of(1, 1, 1, 1));
        maze = new Maze(grid);
    }

    @Test
    @DisplayName("Test getValidNeighbors: checks valid neighbors for a given position")
    void testGetValidNeighbors() {
        Dot startDot = new Dot(1, 1);
        List<Dot> neighbors = CommandFinder.getValidNeighboursFinder(startDot, maze);
        assertEquals(2, neighbors.size());
        assertTrue(neighbors.contains(new Dot(2, 1)), "Neighbors should contain (2,1)");
        assertFalse(neighbors.contains(new Dot(1, 2)), "Neighbors should contain (1,2)");
    }

    @Test
    @DisplayName("Test dotIsValid: verifies dot validity within maze")
    void testDotIsValid() {
        Dot invalidDot = new Dot(0, 0);
        assertTrue(CommandFinder.dotIsValid(invalidDot, maze), "Dot (-1,0) should be invalid.");
    }

    @Test
    @DisplayName("Test init: verifies initialization with specified parameters")
    void testInit() {
        Dot start = new Dot(0, 0);
        Dot end = new Dot(3, 3);
        commandFinder.init(maze, start, end);
        Mockito.verify(commandFinder).init(maze, start, end);
    }

    @Test
    @DisplayName("Test findShortestPath: checks if findShortestPath returns expected path")
    void testFindShortestPath() {
        List<Dot> route = new ArrayList<>();
        route.add(new Dot(1, 1));
        route.add(new Dot(2, 1));
        route.add(new Dot(3, 1));

        List<List<Integer>> expectedGrid = new ArrayList<>(maze.grid());
        when(commandFinder.selectShortestPath(route, maze)).thenReturn(new Maze(expectedGrid));

        Maze resultMaze = commandFinder.selectShortestPath(route, maze);
        assertEquals(expectedGrid, resultMaze.grid(), "The result grid should match the expected shortest path grid.");
    }
}
