package backend.academy.my_project.tests;

import backend.academy.my_project.Word;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WordTest {
    private Word word;

    @BeforeEach
    void setUp() {
        word = new Word(Word.TOPIC_FOOD, "слово", "подсказка");
        word.putWord();
    }

    @Test
    void testConstructor() {
        assertEquals("слово", word.getName(), "Имя должно быть 'тестовое слово'");
        assertEquals("подсказка", word.getHint(), "Подсказка должна быть 'тестовый намек'");
        assertEquals(Word.TOPIC_FOOD, word.getTopic(), "Тема должна быть 'еда'");
    }

    @Test
    void testPutWord() {
        List<String> topics = word.getTopics();
        assertTrue(topics.contains(Word.TOPIC_FOOD), "Тема 'еда' должна быть добавлена");
        assertTrue(topics.contains(Word.TOPIC_CITY), "Тема 'город' должна быть добавлена");
    }

    @Test
    void testSelectWord() {
        Word selectedWord = word.selectWord(Word.TOPIC_FOOD);
        assertNotNull(selectedWord, "Выбранное слово не должно быть null");
        assertEquals(Word.TOPIC_FOOD, selectedWord.getTopic(), "Тема выбранного слова должна быть 'еда'");
    }

    @Test
    void testLetterValid() {
        assertEquals(1, Word.letterValid("а"), "Буква 'а' должна быть валидной");
        assertEquals(0, Word.letterValid("1"), "Цифра не должна быть валидной");
        assertEquals(0, Word.letterValid("А"), "Буква 'А' не должна быть валидной, если использована ранее");
    }

    @Test
    void testGetTopics() {
        List<String> topics = word.getTopics();
        assertTrue(topics.contains(Word.TOPIC_FOOD), "Тема 'еда' должна быть доступна");
        assertTrue(topics.contains(Word.TOPIC_CITY), "Тема 'город' должна быть доступна");
        assertEquals(2, topics.size(), "Должно быть две доступные темы");
    }

    @Test
    void testRussianAlphabetManagement() {
        Word.setRussianAlphabet("б");
        assertEquals("б", word.getRussianAlphabet(), "Русский алфавит должен содержать 'б'");
    }

    @Test
    void testInvalidLetter() {
        assertEquals(0, Word.letterValid("1"), "Цифра не должна быть валидной");
        assertEquals(0, Word.letterValid("@"), "Специальный символ не должен быть валидным");
    }

    @Test
    void testDuplicateLetter() {
        Word.setRussianAlphabet("а");
        assertEquals(0, Word.letterValid("а"), "Данная буква уже была использована");
    }
}
