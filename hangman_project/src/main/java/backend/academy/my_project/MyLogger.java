package backend.academy.my_project;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class MyLogger {

    static ConsolePrinter consolePrinter = new ConsolePrinter();

    private MyLogger() {
    }

    private static final String LOG_FILE = "application.log";

    public static void log(String message) {
        Path logFilePath = Paths.get(LOG_FILE);
        try {
            if (Files.notExists(logFilePath)) {
                Files.createFile(logFilePath);
            }

            try (BufferedWriter writer = Files.newBufferedWriter(logFilePath, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now(); // Получаем текущее время
                String temp = formatter.format(now) + " - " + message + "\n";
                writer.write(temp);
            }
        } catch (NoSuchFileException e) {
        } catch (IOException e) {
        }
    }
}
