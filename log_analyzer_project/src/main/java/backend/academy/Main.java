package backend.academy;


import backend.academy.services.LogProcessor;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        LogProcessor logProcessor = new LogProcessor();
        logProcessor.service(String.join(" ", args));
    }
}
