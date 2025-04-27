package my_project.tests.optionsTests;

import backend.academy.options.ToParameter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ToParameterTest {

    private ToParameter toParameterProcessor;

    @BeforeEach
    void setUp() {
        toParameterProcessor = new ToParameter();
    }

    @Test
    void testDoParameterWithValidArg() {

        String parameter = "--to";
        String validArg = "2024-11-18";
        Map<String, Map<String, String>> allInformation = new LinkedHashMap<>();

        Map<String, Map<String, String>> result = toParameterProcessor.doParameter(parameter, validArg, allInformation);

        assertNotNull(result);
        assertTrue(result.containsKey("limiters"));
    }

    @Test
    void testDoParameterWithInvalidArg() {

        String parameter = "--to";
        String invalidArg = "invalid-date-format";
        Map<String, Map<String, String>> allInformation = new LinkedHashMap<>();

        Map<String, Map<String, String>> result = toParameterProcessor.doParameter(parameter, invalidArg, allInformation);
        assertNotNull(result);
        assertFalse(result.containsKey("limiters"));
    }

    @Test
    void testDoParameterWithNullAllInformation() {

        String parameter = "--to";
        String validArg = "2024-11-18";
        Map<String, Map<String, String>> allInformation = null;

        Map<String, Map<String, String>> result = toParameterProcessor.doParameter(parameter, validArg, allInformation);
        assertNotNull(result);
        assertTrue(result.containsKey("limiters"));
    }

    @Test
    void testDoParameterWithNullArg() {

        String parameter = "--to";
        String nullArg = null;
        Map<String, Map<String, String>> allInformation = new LinkedHashMap<>();

        Map<String, Map<String, String>> result = toParameterProcessor.doParameter(parameter, nullArg, allInformation);

        assertNotNull(result);
        assertFalse(result.containsKey("limiters"));
    }

    @Test
    void testDoParameterWithEmptyArg() {

        String parameter = "--to";
        String emptyArg = "";
        Map<String, Map<String, String>> allInformation = new LinkedHashMap<>();

        Map<String, Map<String, String>> result = toParameterProcessor.doParameter(parameter, emptyArg, allInformation);

        assertNotNull(result);
        assertFalse(result.containsKey("limiters"));
    }


}

