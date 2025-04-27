package backend.academy.options;

import backend.academy.print.ConsolePrinter;
import backend.academy.utilits.LogLine;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FormatParameter implements Parameter {

    ConsolePrinter consolePrinter = new ConsolePrinter();

    @Override
    public Map<String, Map<String, String>> doParameter(
        String parameter,
        String arg,
        Map<String, Map<String, String>> allInformation
    ) {

        if (allInformation == null) {
            consolePrinter.println("Не удалось распознать данные");
            return null;
        }
        if ("markdown".equals(arg.toLowerCase())) {
            formatAsMarkdown();
            return null;
        } else if ("adoc".equals(arg.toLowerCase())) {
            formatAsAdoc(allInformation);
            return null;
        }
        consolePrinter.println("Формат не поддерживается");
        return null;
    }

    private List<LogLine> formatAsMarkdown() {
        consolePrinter.println("В доработке");
        return new ArrayList<>();
    }

    public void formatAsAdoc(Map<String, Map<String, String>> allInformation) {

        for (Map.Entry<String, Map<String, String>> outerEntry : allInformation.entrySet()) {
            String outerKey = outerEntry.getKey();
            Map<String, String> innerMap = outerEntry.getValue();
            if (!"limiters".equals(outerKey)) {
                consolePrinter.println(outerKey);
                printInformation(innerMap);
                consolePrinter.println("");
            }
        }

    }

    public void printInformation(Map<String, String> map) {
        Integer maxValueLength = maxValueLength(map);
        Integer maxKeyLength = maxKeyLength(map);
        int count = 0;
        String separator = " | ";
        for (Map.Entry<String, String> entry : map.entrySet()) {
            consolePrinter.println(
                "| " + entry.getKey() + (" ".repeat(maxKeyLength - entry.getKey().length())) + separator
                    + entry.getValue()
                    + " ".repeat(maxValueLength - entry.getValue().length()) + " |");
            if (count == 0) {
                consolePrinter.println("| " + "-".repeat(maxKeyLength) + separator + "-".repeat(maxValueLength) + " |");

            }
            count = 1;
        }

    }

}
