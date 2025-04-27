package backend.academy.services;

import backend.academy.command.Command;
import backend.academy.parsers.CommandParser;
import backend.academy.utilits.ParameterWithArg;
import java.util.List;
import java.util.Map;

public class LogProcessor {
    private final CommandParser commandParser = new CommandParser();

    public void service(String arg) {
        Map<Command, List<ParameterWithArg>> commandListMap = commandParser.parse(arg);
        Command command;
        for (Map.Entry<Command, List<ParameterWithArg>> entry : commandListMap.entrySet()) {
            command = entry.getKey();
            command.doCommand(entry.getValue());
        }
    }
}
