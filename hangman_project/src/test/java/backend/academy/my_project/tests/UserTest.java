package backend.academy.my_project.tests;

import backend.academy.my_project.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {
    private User user;

    @BeforeEach
    void setUp() {
        user = new User("Игрок", "нормально", "город");
    }

    @Test
    void testUserConstructor() {
        assertEquals("Игрок", user.getName(), "Имя должно быть 'Игрок'");
        assertEquals("нормально", user.getLevel(), "Уровень должен быть 'нормально'");
        assertEquals("город", user.getTopic(), "Тема должна быть 'город'");
    }

    @Test
    void testSetName() {
        user.setName("Плеер");
        assertEquals("Плеер", user.getName(), "Имя должно быть 'Player2'");
    }

    @Test
    void testSetLevel() {
        user.setLevel("сложно");
        assertEquals("сложно", user.getLevel(), "Уровень должен быть 'сложно'");
    }

    @Test
    void testSetTopic() {
        user.setTopic("еда");
        assertEquals("еда", user.getTopic(), "Тема должна быть 'наука'");
    }

    @Test
    void testToString() {
        // Проверка метода toString
        String expectedString = "User{name='Игрок', level='нормально', topic='город'}";
        assertEquals(expectedString, user.toString(), "Метод toString должен вернуть корректную строку");
    }
}
