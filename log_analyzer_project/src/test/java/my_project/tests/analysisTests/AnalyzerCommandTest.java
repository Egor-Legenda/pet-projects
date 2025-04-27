package my_project.tests.analysisTests;

import backend.academy.analysis.AnalyzerCommand;
import backend.academy.options.*;
import backend.academy.utilits.ParameterWithArg;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;


public class AnalyzerCommandTest {

    private AnalyzerCommand analyzerCommand;
    private Parameter mockParameter;

    @BeforeEach
    public void setUp() {
        analyzerCommand = new AnalyzerCommand();
        mockParameter = Mockito.mock(Parameter.class);
    }

    @Test
    public void testReorderByOptions() {
        List<ParameterWithArg> inputList = List.of(
            new ParameterWithArg("--filter-value", "200"),
            new ParameterWithArg("--from", "2021-01-01"),
            new ParameterWithArg("--to", "2021-12-31"),
            new ParameterWithArg("--filter-field", "status")
        );

        Map<String, Parameter> parameterMap = analyzerCommand.setParameterMap();
        Map<Integer, ParameterWithArg> reorderedMap = analyzerCommand.reorderByOptions(inputList, parameterMap);

        assertEquals("--from", reorderedMap.get(0).options());
        assertEquals("--to", reorderedMap.get(1).options());
        assertEquals("--filter-field", reorderedMap.get(2).options());
        assertEquals("--filter-value", reorderedMap.get(3).options());
    }

    @Test
    public void testSetParameterMap() {
        Map<String, Parameter> parameterMap = analyzerCommand.setParameterMap();

        assertEquals(6, parameterMap.size());
        assertTrue(parameterMap.containsKey("--from"));
        assertTrue(parameterMap.containsKey("--to"));
        assertTrue(parameterMap.containsKey("--filter-field"));
        assertTrue(parameterMap.containsKey("--filter-value"));
        assertTrue(parameterMap.containsKey("--path"));
        assertTrue(parameterMap.containsKey("--format"));
    }

    @Test
    public void testEqualsAndHashCode() {
        AnalyzerCommand anotherAnalyzerCommand = new AnalyzerCommand();

        assertEquals(analyzerCommand, anotherAnalyzerCommand);
        assertEquals(analyzerCommand.hashCode(), anotherAnalyzerCommand.hashCode());
    }
}

