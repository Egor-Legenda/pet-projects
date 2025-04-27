package backend.academy.my_project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


public class ReaderLine {

    private final BufferedReader reader;

    public ReaderLine() {
        this.reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
    }

    public ReaderLine(BufferedReader reader) {
        this.reader = reader;
    }

    public String read() {
        try {
            String input = reader.readLine();
            if (input != null) {
                return input.trim();
            } else {
                return null;
            }
        } catch (IOException e) {
            return null;
        }
    }

}
