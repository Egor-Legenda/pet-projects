package backend.academy.my_project.tests;

import backend.academy.my_project.ConsolePrinter;
import backend.academy.my_project.Mechanism;
import backend.academy.my_project.ReaderLine;
import backend.academy.my_project.User;
import backend.academy.my_project.Word;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MechanismTest {
    private Mechanism mechanism;
    private ConsolePrinter consolePrinterMock;
    private ReaderLine readerMock;
    private Word wordMock;
    private User userMock;
    private SecureRandom secureRandomStub;

    @BeforeEach
    void setUp() {
        consolePrinterMock = mock(ConsolePrinter.class);
        readerMock = mock(ReaderLine.class);

        wordMock = mock(Word.class);
        userMock = mock(User.class);
        secureRandomStub = new SecureRandom() {
            @Override
            public int nextInt(int bound) {
                return 0;
            }
        };
        mechanism = spy(new Mechanism(secureRandomStub));
        mechanism.reader(readerMock);
        mechanism.consolePrinter(consolePrinterMock);
        mechanism.consolePrinter = consolePrinterMock;
        mechanism.reader = readerMock;
        mechanism.wordsCreator = wordMock;
    }

    @Test
    void testStartWithNewUser() throws InterruptedException {
        when(readerMock.read()).thenReturn("Игрок")
            .thenReturn("нормально")
            .thenReturn("тест")
            .thenReturn("-")
            .thenReturn("-");

        LinkedList<String> topics = new LinkedList<>();
        topics.add("тест");
        when(wordMock.getTopics()).thenReturn(topics);
        when(wordMock.selectWord("тест"))
            .thenReturn(new Word("тема", "тест", "подсказка"));
        mechanism.start();
        verify(consolePrinterMock).printlnRed("* Для повторного просмотра подсказки нажмите любой символ не из русского алфавита");
        verify(consolePrinterMock).println("Слово состоит из 4 букв ");
        verify(consolePrinterMock).println("Подсказка: подсказка");
        verify(consolePrinterMock).print("Слово может состоять только из русских букв. Введите букву: ");
    }

    @Test
    void testStartWithExistingUser() throws InterruptedException {
        when(readerMock.read()).thenReturn("легко")
            .thenReturn("-")
            .thenReturn("-")
            .thenReturn("-");

        LinkedList<String> topics = new LinkedList<>();
        topics.add("тест");
        when(wordMock.getTopics()).thenReturn(topics);
        when(wordMock.selectWord(anyString())).thenReturn(new Word("тема", "тест", "подсказка"));
        mechanism.start();
        verify(consolePrinterMock).printlnRed("* Для повторного просмотра подсказки нажмите любой символ не из русского алфавита");
        verify(consolePrinterMock).println("Слово состоит из 4 букв ");
        verify(consolePrinterMock).println("Подсказка: подсказка");
        verify(consolePrinterMock).print("Слово может состоять только из русских букв. Введите букву: ");

    }

    @Test
    void testStartGameEnd() throws InterruptedException {


        when(readerMock.read()).thenReturn("Игрок")
            .thenReturn("нормально")
            .thenReturn("тема")
            .thenReturn("т")
            .thenReturn("е")
            .thenReturn("с")
            .thenReturn("-");
        LinkedList<String> topics = new LinkedList<>();
        topics.add("тест");
        when(wordMock.getTopics()).thenReturn(topics);
        when(wordMock.selectWord(anyString())).thenReturn(new Word("тема", "тест", "подсказка"));
        mechanism.start();
        verify(consolePrinterMock).println("Вы угадали слово тест");
    }

    @Test
    void testPlayMethod() throws InterruptedException {
        doNothing().when(mechanism).start();
        when(readerMock.read()).thenReturn("-");
        mechanism.play();
        verify(consolePrinterMock).print("Хотите продолжить(+,-)?");
        verify(consolePrinterMock).print("Игра окончена");
    }

    @Test
    void testChoiceDifficulty() throws InterruptedException {
        when(readerMock.read()).thenReturn("нормально");
        String difficulty = mechanism.choiceDifficulty();
        assertEquals("нормально", difficulty);
        verify(consolePrinterMock).print("Выберете уровень сложности(легко, нормально, сложно): ");
    }

    @Test
    void testChoiseTopic() throws InterruptedException {
        LinkedList<String> topics = new LinkedList<>();
        topics.add("природа");
        topics.add("технологии");
        when(wordMock.getTopics()).thenReturn(topics);
        when(readerMock.read()).thenReturn("природа");
        String topic = mechanism.choiseTopic();
        assertEquals("природа", topic);
        verify(consolePrinterMock).print("Выберете тему (природа, технологии): ");
    }

    @Test
    void testRegistration() throws InterruptedException {
        when(readerMock.read()).thenReturn("Player1", "нормально", "город");
        LinkedList<String> topics = new LinkedList<>();
        topics.add("город");
        when(wordMock.getTopics()).thenReturn(topics);
        User user = mechanism.registration();
        assertNotNull(user);
        assertEquals("Player1", user.getName());
        assertEquals("нормально", user.getLevel());
        assertEquals("город", user.getTopic());
        verify(consolePrinterMock).print("Введите никнейм: ");
    }
}
