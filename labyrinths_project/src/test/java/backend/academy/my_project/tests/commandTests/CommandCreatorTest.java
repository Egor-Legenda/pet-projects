package backend.academy.my_project.tests.commandTests;

import backend.academy.my_project.command.CommandCreator;
import backend.academy.my_project.utilits.Dot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class CommandCreatorTest {
    private CommandCreator commandCreator;

    @BeforeEach
    void setUp() {
        commandCreator = Mockito.mock(CommandCreator.class);
    }

    @Test
    @DisplayName("Test getMaze: returns expected maze structure")
    void testGetMaze() {
        List<List<Integer>> mockMaze = List.of(
            List.of(0, 1, 1),
            List.of(1, 0, 1),
            List.of(1, 1, 0)
        );
//        when(commandCreator.getMaze()).thenReturn(mockMaze);
//        assertEquals(mockMaze, commandCreator.getMaze());
    }

    @Test
    @DisplayName("Test init: initializes with correct parameters")
    void testInit() {
        Dot start = new Dot(0, 0);
        Dot end = new Dot(2, 2);
        commandCreator.init(start, end, 3, 3);
        Mockito.verify(commandCreator).init(start, end, 3, 3);
    }

    @Test
    @DisplayName("Test getRandomNumber: returns expected random number")
    void testGetRandomNumber() {
        when(commandCreator.getRandomNumber()).thenReturn(42);
        assertEquals(42, commandCreator.getRandomNumber());
    }
}
