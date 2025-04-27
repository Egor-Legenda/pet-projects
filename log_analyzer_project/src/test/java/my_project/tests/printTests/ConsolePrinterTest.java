package my_project.tests.printTests;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import backend.academy.print.ConsolePrinter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConsolePrinterTest {
    private ByteArrayOutputStream outputStreamCaptor;
    private ConsolePrinter consolePrinter;
    private static final String ANSI_RED = "[31m";
    private static final String ANSI_RESET = "\u001B[0m";

    @BeforeEach
    public void setUp() {
        outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        consolePrinter = new ConsolePrinter();
    }

    @AfterEach
    public void tearDown() {
        System.setOut(System.out);
    }

    @Test
    @DisplayName("Test default print")
    public void testPrint() {
        consolePrinter.print("Hello");
        assertEquals("Hello", outputStreamCaptor.toString().trim());
    }

    @Test
    @DisplayName("Test print with new line")
    public void testPrintln() {
        consolePrinter.println("Hello, World!");
        assertEquals("Hello, World!", outputStreamCaptor.toString().trim());
    }

    @Test
    @DisplayName("Test print in red color")
    public void testPrintRed() {
        consolePrinter.printRed("Error message");
        assertEquals(ANSI_RED + "Error message" +  ANSI_RESET, outputStreamCaptor.toString().trim());
    }

    @Test
    @DisplayName("Test print in red color with new line")
    public void testPrintlnRed() {
        consolePrinter.printlnRed("Error message");
        assertEquals("[31mError message\u001B[0m", outputStreamCaptor.toString().trim());
    }
}
