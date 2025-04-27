package my_project.tests.readerTests;

import backend.academy.reader.Reader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReaderTest {

    @Test
    @DisplayName("Should read input correctly from ConsoleReader implementation")
    public void testRead() {
        Reader mockReader = mock(Reader.class);
        when(mockReader.read()).thenReturn("Test input");

        String result = mockReader.read();

        assertThat(result).isEqualTo("Test input");
    }
}

