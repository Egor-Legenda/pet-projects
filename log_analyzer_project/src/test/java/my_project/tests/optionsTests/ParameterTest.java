package my_project.tests.optionsTests;

import backend.academy.options.Parameter;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ParameterTest {

    private final Parameter parameter = new Parameter() {
        @Override
        public Map<String, Map<String, String>> doParameter(String parameter, String arg, Map<String, Map<String, String>> allInformation) {
            return null;
        }
    };

    @Test
    void testMaxKeyLength() {
        Map<String, String> map = Map.of(
            "short", "value1",
            "muchlongerkey", "value2",
            "mediumkey", "value3"
        );

        int result = parameter.maxKeyLength(map);
        assertEquals(13, result, "Длина самого длинного ключа должна быть 13");
    }

    @Test
    void testMaxValueLength() {
        Map<String, String> map = Map.of(
            "key1", "short",
            "key2", "verylongvalue",
            "key3", "medium"
        );

        int result = parameter.maxValueLength(map);
        assertEquals(13, result, "Длина самого длинного значения должна быть 13");
    }

    @Test
    void testCountResource() {
        Map<String, String> resourceCount = new HashMap<>();
        resourceCount.put("resource1", "2");

        Map<String, String> result = parameter.countResource(resourceCount, "resource1");
        assertEquals("3", result.get("resource1"), "Счётчик ресурса должен увеличиться на 1");

        result = parameter.countResource(resourceCount, "resource2");
        assertEquals("1", result.get("resource2"), "Новый ресурс должен быть добавлен со значением 1");
    }

    @Test
    void testSizeResponse() {
        List<Integer> sizes = new ArrayList<>(List.of(100, 200, 300));
        double result = parameter.sizeResponse(sizes);

        assertEquals(300, result, "95-й процентиль должен быть 400");
    }

    @Test
    void testCalculateAverageSize() {
        double currentAverage = 150.0;
        int currentCount = 2;
        int newSize = 300;

        double result = parameter.calculateAverageSize(currentAverage, currentCount, newSize);
        assertEquals(200.0, result, 0.01, "Новое среднее значение должно быть 200.0");
    }


}

