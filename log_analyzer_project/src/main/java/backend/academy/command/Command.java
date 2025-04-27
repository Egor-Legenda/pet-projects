package backend.academy.command;

import backend.academy.utilits.ParameterWithArg;
import java.util.List;

public interface Command {
    void doCommand(List<ParameterWithArg> parameterWithArgs);

}
