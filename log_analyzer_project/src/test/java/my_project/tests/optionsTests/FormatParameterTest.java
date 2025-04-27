package my_project.tests.optionsTests;

import backend.academy.options.FormatParameter;
import backend.academy.print.ConsolePrinter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class FormatParameterTest {

    @Mock
    private ConsolePrinter mockConsolePrinter;

    @InjectMocks
    private FormatParameter formatParameter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testDoParameterWithNullAllInformation() {

        formatParameter.doParameter("--format", "adoc", null);

        verify(mockConsolePrinter, times(1)).println("Не удалось распознать данные");
    }

}

