package my_project.tests.utilitsTests;

import backend.academy.utilits.NumberUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NumberUtilTest {

    @Test
    void testEnumValues() {
        assertEquals(1000000, NumberUtil.MILLION.number());
        assertEquals(1000, NumberUtil.THOUSAND.number());
        assertEquals(360, NumberUtil.DEGREE.number());
        assertEquals(255, NumberUtil.COLOR_MAX.number());
        assertEquals(16, NumberUtil.SIXTEEN.number());
        assertEquals(8, NumberUtil.EIGHT.number());
        assertEquals(7, NumberUtil.SEVEN.number());
        assertEquals(6, NumberUtil.SIX.number());
        assertEquals(5, NumberUtil.FIFE.number());
        assertEquals(4, NumberUtil.FOUR.number());
        assertEquals(3, NumberUtil.THREE.number());
        assertEquals(2, NumberUtil.TWO.number());
        assertEquals(1, NumberUtil.ONE.number());
        assertEquals(-20, NumberUtil.START_CYCLE.number());
    }

    @Test
    void testEnumIntegrity() {
        var uniqueNumbers = new java.util.HashSet<>();
        for (NumberUtil value : NumberUtil.values()) {
            assertTrue(uniqueNumbers.add(value.number()),
                "Duplicate number found: " + value.number());
        }
    }

    @Test
    void testEnumName() {
        assertEquals("MILLION", NumberUtil.MILLION.name());
        assertEquals("THOUSAND", NumberUtil.THOUSAND.name());
        assertEquals("DEGREE", NumberUtil.DEGREE.name());
    }

    @Test
    void testEnumCount() {
        assertEquals(14, NumberUtil.values().length,
            "Unexpected number of enum constants");
    }
}
