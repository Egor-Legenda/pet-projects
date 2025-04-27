package my_project.tests.commandTests;

import backend.academy.command.Command;
import backend.academy.utilits.ParameterWithArg;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

public class CommandTest {

    private Command testCommand;
    private List<ParameterWithArg> parameterList;

    private static class TestCommandImpl implements Command {
        private List<ParameterWithArg> capturedParameters = new ArrayList<>();

        @Override
        public void doCommand(List<ParameterWithArg> parameterWithArgs) {
            this.capturedParameters.addAll(parameterWithArgs);
        }

        public List<ParameterWithArg> getCapturedParameters() {
            return capturedParameters;
        }
    }

    @BeforeEach
    public void setUp() {
        testCommand = new TestCommandImpl();
        parameterList = new ArrayList<>();
        parameterList.add(new ParameterWithArg("option1", "arg1"));
        parameterList.add(new ParameterWithArg("option2", "arg2"));
    }

    @Test
    public void testDoCommandWithParameters() {
        testCommand.doCommand(parameterList);
        assertEquals(2, ((TestCommandImpl) testCommand).getCapturedParameters().size());
        assertEquals("option1", ((TestCommandImpl) testCommand).getCapturedParameters().get(0).options());
        assertEquals("arg1", ((TestCommandImpl) testCommand).getCapturedParameters().get(0).arg());
    }

    @Test
    public void testDoCommandWithEmptyParameters() {
        List<ParameterWithArg> emptyList = new ArrayList<>();
        testCommand.doCommand(emptyList);

        assertEquals(0, ((TestCommandImpl) testCommand).getCapturedParameters().size());
    }

    @Test
    public void testDoCommandUsingMockito() {
        Command mockCommand = Mockito.mock(Command.class);

        mockCommand.doCommand(parameterList);
        verify(mockCommand, times(1)).doCommand(parameterList);
    }
}
