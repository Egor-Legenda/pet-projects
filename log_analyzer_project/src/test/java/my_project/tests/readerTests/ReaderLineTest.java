package my_project.tests.readerTests;

import java.io.BufferedReader;
import java.io.IOException;
import backend.academy.reader.ReaderLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReaderLineTest {
    private ReaderLine readerLine;
    private BufferedReader mockBufferedReader;

    @BeforeEach
    void setUp() {
        mockBufferedReader = mock(BufferedReader.class);
        readerLine = new ReaderLine(mockBufferedReader);
    }

    @Test
    @DisplayName("Test examination reader")
    void testInputSuccess() throws IOException {
        String expectedInput = "Проверка";
        when(mockBufferedReader.readLine()).thenReturn(expectedInput);
        String actualInput = readerLine.read();
        assertEquals(expectedInput, actualInput);
    }

    @Test
    @DisplayName("Test call exception")
    void testInputIOException() throws IOException {
        when(mockBufferedReader.readLine()).thenThrow(new IOException());
        String actualInput = readerLine.read();
        assertNull(actualInput);
    }
}

