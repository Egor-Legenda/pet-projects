package backend.academy.my_project.tests;

import backend.academy.my_project.ReaderLine;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

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
    void testInputSuccess() throws IOException {
        String expectedInput = "Проверка";
        when(mockBufferedReader.readLine()).thenReturn(expectedInput);
        String actualInput = readerLine.read();
        assertEquals(expectedInput, actualInput);
    }

    @Test
    void testInputIOException() throws IOException {
        when(mockBufferedReader.readLine()).thenThrow(new IOException());
        String actualInput = readerLine.read();
        assertNull(actualInput);
    }
}
