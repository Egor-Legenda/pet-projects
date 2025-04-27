package backend.academy.my_project.tests.mazeGenerationTests;

import backend.academy.my_project.bfs.BfsFinder;
import backend.academy.my_project.command.CommandCreator;
import backend.academy.my_project.command.CommandFinder;
import backend.academy.my_project.dfs.DfsCreator;
import backend.academy.my_project.mazeGeneration.MazeGeneration;
import backend.academy.my_project.print.ConsolePrinter;
import backend.academy.my_project.reader.ReaderLine;
import backend.academy.my_project.utilits.Dot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MazeGenerationTest {

    private MazeGeneration mazeGeneration;
    private ReaderLine readerLineMock;
    private ConsolePrinter consolePrinterMock;

    @BeforeEach
    void setUp() {
        readerLineMock = mock(ReaderLine.class);
        consolePrinterMock = mock(ConsolePrinter.class);
        mazeGeneration = new MazeGeneration();
        mazeGeneration.consolePrinter(consolePrinterMock);
        mazeGeneration.readerLine(readerLineMock);
    }

    @Test
    @DisplayName("Test depth configuration: verifies depth input is configured correctly")
    void testConfigureDepth() {
        when(readerLineMock.read()).thenReturn("3");
        int depth = mazeGeneration.configureDepth();
        assertEquals(3, depth);
        verify(consolePrinterMock, times(1)).print("Введите глубину лабиринта(<=50): ");
    }

    @Test
    @DisplayName("Test width configuration: verifies width input is configured correctly")
    void testConfigureWidth() {
        when(readerLineMock.read()).thenReturn("4");
        int width = mazeGeneration.configureWidth();
        assertEquals(4, width);
        verify(consolePrinterMock, times(1)).print("Введите ширину лабиринта(<=50): ");
    }

    @Test
    @DisplayName("Test starting dot configuration: verifies correct setting of starting point")
    void testConfigureDot() throws NoSuchFieldException, IllegalAccessException {
        when(readerLineMock.read()).thenReturn("3 4");
        setPrivateField(mazeGeneration, "width", 9);
        setPrivateField(mazeGeneration, "depth", 9);
        Dot dot = mazeGeneration.configureDot("начала");
        assertNotNull(dot);
        assertEquals(2, dot.x());
        assertEquals(3, dot.y());
        verify(consolePrinterMock, times(1)).print("Введите координаты точки начала начиная с точки 1 1 через пробел (x y):");
    }

    @Test
    @DisplayName("Test dot configuration with invalid input: checks for prompt on invalid input")
    void testConfigureDotInvalidInput() throws NoSuchFieldException, IllegalAccessException {
        when(readerLineMock.read()).thenReturn("10 10", "3 4");
        setPrivateField(mazeGeneration, "width", 9);
        setPrivateField(mazeGeneration, "depth", 9);
        Dot dot = mazeGeneration.configureDot("конца");
        assertNotNull(dot);
        assertEquals(2, dot.x());
        assertEquals(3, dot.y());
        verify(consolePrinterMock, atLeastOnce()).print("Координаты должны быть положительными. Попробуйте снова: ");
    }

    @Test
    @DisplayName("Test valid algorithm creator selection: checks if valid algorithm is selected")
    void testIsValidAlgorithmCreatorValid() {
        HashMap<String, CommandCreator> commands = new HashMap<>();
        commands.put("dfs", new DfsCreator());
        String algorithm = mazeGeneration.isValidAlgorithmCreator("dfs", commands);
        assertEquals("dfs", algorithm);
    }

    @Test
    @DisplayName("Test invalid algorithm creator selection: validates default algorithm on invalid input")
    void testIsValidAlgorithmCreatorInvalid() {
        HashMap<String, CommandCreator> commands = new HashMap<>();
        commands.put("dfs", new DfsCreator());
        when(readerLineMock.read()).thenReturn("dfs");
        String algorithm = mazeGeneration.isValidAlgorithmCreator("invalid", commands);
        assertEquals("dfs", algorithm);
    }

    @Test
    @DisplayName("Test valid algorithm finder selection: checks if valid algorithm is selected")
    void testIsValidAlgorithmFinderValid() {
        HashMap<String, CommandFinder> commands = new HashMap<>();
        commands.put("bfs", new BfsFinder());
        String algorithm = mazeGeneration.isValidAlgorithmFinder("bfs", commands);
        assertEquals("bfs", algorithm);
    }

    @Test
    @DisplayName("Test invalid algorithm finder selection: validates default algorithm on invalid input")
    void testIsValidAlgorithmFinderInvalid() {
        HashMap<String, CommandFinder> commands = new HashMap<>();
        commands.put("bfs", new BfsFinder());
        when(readerLineMock.read()).thenReturn("bfs");
        String algorithm = mazeGeneration.isValidAlgorithmFinder("invalid", commands);
        assertEquals("bfs", algorithm);
    }

    @Test
    @DisplayName("Test maze drawing: verifies maze drawing with correct output")
    void testDrawMaze() throws NoSuchFieldException, IllegalAccessException {
        List<List<Integer>> maze = new ArrayList<>(List.of(
            new ArrayList<>(List.of(1, 1, 1)),
            new ArrayList<>(List.of(0, 5, 0)),
            new ArrayList<>(List.of(1, 1, 1))
        ));
        setPrivateField(mazeGeneration, "start", new Dot(1, 1));
        setPrivateField(mazeGeneration, "end", new Dot(1, 1));
        mazeGeneration.drawMaze(maze);
        verify(consolePrinterMock, atLeastOnce()).print(anyString());
    }

    @Test
    @DisplayName("Test positive price display: checks price display for positive route cost")
    void testPrintPricePositive() {
        mazeGeneration.printPrice(10.0);
        verify(consolePrinterMock).println("Данный маршрут вам обойдется в 10.0");
    }

    @Test
    @DisplayName("Test negative price display: checks price display for negative route cost")
    void testPrintPriceNegative() {
        mazeGeneration.printPrice(-5.0);
        verify(consolePrinterMock).println("За эту поездку вам заплатят -5.0");
    }

    private void setPrivateField(Object target, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

}
