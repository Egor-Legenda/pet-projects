package backend.academy.parsers;

import backend.academy.analysis.AnalyzerCommand;
import backend.academy.command.Command;
import backend.academy.error.UnknownCommand;
import backend.academy.utilits.ParameterWithArg;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CommandParser {
    private final Map<String, Command> commandMap = new LinkedHashMap<>(Map.of("analyzer", new AnalyzerCommand()));
    private Command command = null;
    private String options = "";
    private String arg = "";
    private Map<Command, List<ParameterWithArg>> commandWithOptions = new LinkedHashMap<>();

    public Map<Command, List<ParameterWithArg>> parse(String str) {
        resetState();
        String commandLine = str.trim();
        String[] commandArray = commandLine.split("");

        for (int i = 0; i < commandArray.length; i++) {
            String currentChar = commandArray[i];
            if (" ".equals(currentChar)) {
                processSpace(commandArray, i);
            } else {
                processCharacter(currentChar, i, commandArray.length);
            }
        }
        return commandWithOptions;
    }

    private void resetState() {
        command = null;
        options = "";
        arg = "";
        commandWithOptions = new LinkedHashMap<>();
    }

    private void processSpace(String[] commandArray, int index) {
        if (!arg.isEmpty()) {
            if (arg.startsWith("--")) {
                handleOption();
            } else if (options.isEmpty()) {
                handleCommand();
            } else {
                handleParameter(commandArray, index);
            }
        }
    }

    private void processCharacter(String currentChar, int index, int arrayLength) {
        arg += currentChar;

        if (index == arrayLength - 1) {
            finalizeParsing();
        }
    }

    private void handleOption() {
        options = arg;
        arg = "";
    }

    private void handleCommand() {
        commandWithOptions = findCommand(arg);
        if (commandWithOptions == null) {
            handleUnknownCommand();
        } else {
            command = commandWithOptions.keySet().iterator().next();
            arg = "";
        }
    }

    private void handleParameter(String[] commandArray, int index) {
        if (index + 1 < commandArray.length && "-".equals(commandArray[index + 1])) {
            ensureCommandExists();
            commandWithOptions.get(command).add(new ParameterWithArg(options, arg));
            arg = "";
        }
    }

    private void finalizeParsing() {
        if (command == null) {
            handleUnknownCommand();
        } else {
            ensureCommandExists();
            commandWithOptions.get(command).add(new ParameterWithArg(options, arg));
        }
        arg = "";
    }

    private void handleUnknownCommand() {
        commandWithOptions = new LinkedHashMap<>();
        commandWithOptions.put(new UnknownCommand(), new ArrayList<>());
    }

    private Map<Command, List<ParameterWithArg>> findCommand(String arg) {
        if (commandMap.containsKey(arg.toLowerCase())) {
            Command cmd = commandMap.get(arg.toLowerCase());
            Map<Command, List<ParameterWithArg>> result = new LinkedHashMap<>();
            result.put(cmd, new ArrayList<>());
            return result;
        }
        return null;
    }

    private void ensureCommandExists() {
        if (!commandWithOptions.containsKey(command)) {
            commandWithOptions.put(command, new ArrayList<>());
        }
    }

    public static <K, V> K getKeyByValue(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
