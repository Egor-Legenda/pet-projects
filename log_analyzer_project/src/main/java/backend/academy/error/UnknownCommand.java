package backend.academy.error;

import backend.academy.command.Command;
import backend.academy.print.ConsolePrinter;
import backend.academy.utilits.ParameterWithArg;
import java.util.List;

public class UnknownCommand implements Command {
    private final ConsolePrinter consolePrinter = new ConsolePrinter();

    @Override
    public void doCommand(List<ParameterWithArg> parameterWithArgs) {
        consolePrinter.println("Ошибка");
    }

}
