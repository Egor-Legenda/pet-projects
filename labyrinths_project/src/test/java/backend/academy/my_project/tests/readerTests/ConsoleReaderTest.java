package backend.academy.my_project.tests.readerTests;

import backend.academy.my_project.reader.ConsoleReader;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ConsoleReaderTest {

    @Test
    @DisplayName("Should read input correctly from ConsoleReader implementation")
    public void testRead() {
        ConsoleReader mockReader = mock(ConsoleReader.class);
        when(mockReader.read()).thenReturn("Test input");

        String result = mockReader.read();

        assertThat(result).isEqualTo("Test input");
    }
}

