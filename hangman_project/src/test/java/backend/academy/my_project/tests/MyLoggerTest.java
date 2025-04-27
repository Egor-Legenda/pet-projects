package backend.academy.my_project.tests;

import backend.academy.my_project.MyLogger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class MyLoggerTest {
    private static final String LOG_FILE = "application.log";
    private MyLogger myLogger = mock(MyLogger.class);

    @BeforeEach
    void setUp() {
        try {
            Path logFilePath = Paths.get(LOG_FILE);
            if (Files.exists(logFilePath)) {
                Files.delete(logFilePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
        try {
            Path logFilePath = Paths.get(LOG_FILE);
            if (Files.exists(logFilePath)) {
                Files.delete(logFilePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testLogCreatesFileAndWritesMessage() throws IOException {
        MyLogger.log("Это тестовое сообщение для лога.");
        Path logFilePath = Paths.get(LOG_FILE);
        assertTrue(Files.exists(logFilePath), "Файл лога должен быть создан.");
        String content = Files.readString(logFilePath);
        assertTrue(content.contains("Это тестовое сообщение для лога."), "Сообщение должно быть записано в файл.");
    }

    @Test
    void testLogWritesMultipleMessages() throws IOException {
        MyLogger.log("Первое сообщение в логе.");
        MyLogger.log("Второе сообщение в логе.");
        Path logFilePath = Paths.get(LOG_FILE);
        assertTrue(Files.exists(logFilePath), "Файл лога должен быть создан.");
        String content = Files.readString(logFilePath);
        assertTrue(content.contains("Первое сообщение в логе."), "Первое сообщение должно быть записано в файл.");
        assertTrue(content.contains("Второе сообщение в логе."), "Второе сообщение должно быть записано в файл.");
    }



    @Test
    void testLogFileNotCreatedOnNoSuchFileException() {
        Path logFilePath = Paths.get(LOG_FILE);
        try {
            if (Files.exists(logFilePath)) {
                Files.delete(logFilePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertTrue(Files.notExists(logFilePath), "Файл лога не должен быть создан при NoSuchFileException.");
    }
}
