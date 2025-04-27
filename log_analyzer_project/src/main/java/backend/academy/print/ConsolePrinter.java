package backend.academy.print;


import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class ConsolePrinter implements Printer {
    private PrintWriter outputStream;
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";

    public ConsolePrinter() {
        this.outputStream = new PrintWriter(System.out, true, StandardCharsets.UTF_8);
    }

    @Override
    public void print(String str) {
        outputStream.write(str);
        outputStream.flush();
    }

    @Override
    public void println(String str) {
        outputStream.write(str + "\n");
        outputStream.flush();
    }

    public void printRed(String str) {
        outputStream.write(ANSI_RED + str + ANSI_RESET);
        outputStream.flush();
    }

    public void printlnRed(String str) {
        outputStream.write(ANSI_RED + str + ANSI_RESET + "\n");
        outputStream.flush();
    }

}
