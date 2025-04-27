package my_project.tests.optionsTests;

import backend.academy.options.FilterParameter;
import org.junit.jupiter.api.Test;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FilterParameterTest {

    private final FilterParameter filterParameter = new FilterParameter();


    @Test
    void testFilterValueDoesNotOverrideExistingValue() {

        Map<String, Map<String, String>> allInformation = new LinkedHashMap<>();
        allInformation.put("limiters", new LinkedHashMap<>(Map.of("fieldName", "existingValue")));

        Map<String, Map<String, String>> result = filterParameter.doParameter("--filter-value", "newValue", allInformation);

        assertNotNull(result);
        assertTrue(result.containsKey("limiters"));
        assertEquals(1, result.get("limiters").size());
        assertEquals("existingValue", result.get("limiters").get("fieldName"));
    }


}
