package backend.academy.my_project.tests;

import backend.academy.my_project.DifficultyLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class DifficultyLevelTest {

    private ByteArrayOutputStream outputStreamCaptor;
    @BeforeEach
    public void setUp() {
        outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
    }



    @Test
    public void testChoiceValidComplexity() {
        String selectedComplexity = DifficultyLevel.choice(DifficultyLevel.EASY);
        assertEquals(DifficultyLevel.EASY, selectedComplexity);
        assertEquals(0, DifficultyLevel.getLevelNow());
    }

    @Test
    public void testChoiceInvalidComplexity() {
        String selectedComplexity = DifficultyLevel.choice("неправильный ввод");
        assertTrue(selectedComplexity.equals(DifficultyLevel.EASY) ||
            selectedComplexity.equals(DifficultyLevel.NORMAL) ||
            selectedComplexity.equals(DifficultyLevel.HARD));
    }

    @Test
    public void testRandomLevel() {
        String randomLevel = DifficultyLevel.randomLevel();
        assertTrue(randomLevel.equals(DifficultyLevel.EASY) ||
            randomLevel.equals(DifficultyLevel.NORMAL) ||
            randomLevel.equals(DifficultyLevel.HARD));
    }

    @Test
    public void testGetKeyByIndex() {
        HashMap<String, Integer> testMap = new HashMap<>();
        testMap.put("легко", 1);
        testMap.put("нормально", 2);
        testMap.put("сложно", 3);
        String key = DifficultyLevel.getKeyByIndex(testMap, 1);
        assertNotNull(key);
        assertTrue(testMap.containsKey(key));
    }

    @Test
    public void testSetAndGetLevelNow() {
        DifficultyLevel.setLevelNow(5);
        assertEquals(5, DifficultyLevel.getLevelNow());
    }
}
