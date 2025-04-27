package my_project.tests.optionsTests;

import backend.academy.options.FromParameter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FromParameterTest {

    private FromParameter fromParameter;

    @BeforeEach
    void setUp() {
        fromParameter = new FromParameter();
    }

    @Test
    void testDoParameterWithValidDate() {

        String validArg = "2023-11-18";
        Map<String, Map<String, String>> inputMap = new LinkedHashMap<>();

        Map<String, Map<String, String>> result = fromParameter.doParameter("--from", validArg, inputMap);

        assertNotNull(result);
        assertTrue(result.containsKey("limiters"));
        assertEquals(1, result.get("limiters").size());
        assertTrue(result.get("limiters").containsKey("startTime"));

        ZonedDateTime expectedTime = fromParameter.parseDate(validArg);
        assertEquals(expectedTime.toString(), result.get("limiters").get("startTime"));
    }

    @Test
    void testDoParameterWithInvalidDate() {
        String invalidArg = "invalid-date";
        Map<String, Map<String, String>> inputMap = new LinkedHashMap<>();
        Map<String, Map<String, String>> result = fromParameter.doParameter("--from", invalidArg, inputMap);
        assertNotNull(result);
        assertFalse(result.containsKey("limiters"));
    }

    @Test
    void testDoParameterWithNullInputMap() {

        String validArg = "2023-11-18";
        Map<String, Map<String, String>> result = fromParameter.doParameter("--from", validArg, null);
        assertNotNull(result);
        assertTrue(result.containsKey("limiters"));
        assertEquals(1, result.get("limiters").size());
        assertTrue(result.get("limiters").containsKey("startTime"));
        ZonedDateTime expectedTime = fromParameter.parseDate(validArg);
        assertEquals(expectedTime.toString(), result.get("limiters").get("startTime"));
    }

    @Test
    void testDoParameterWithNullDateArgument() {
        Map<String, Map<String, String>> inputMap = new LinkedHashMap<>();
        Map<String, Map<String, String>> result = fromParameter.doParameter("--from", null, inputMap);
        assertNotNull(result);
        assertFalse(result.containsKey("limiters"));
    }
}

