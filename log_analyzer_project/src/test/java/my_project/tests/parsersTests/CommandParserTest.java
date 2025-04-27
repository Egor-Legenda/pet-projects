package my_project.tests.parsersTests;

import backend.academy.parsers.CommandParser;
import backend.academy.command.Command;
import backend.academy.analysis.AnalyzerCommand;
import backend.academy.error.UnknownCommand;
import backend.academy.reader.ReaderLine;
import backend.academy.utilits.ParameterWithArg;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommandParserTest {

    private CommandParser parser;


    @BeforeEach
    void setUp() {
        parser = new CommandParser();
    }

    @Test
    void testParseWithValidCommand() {

        var result = parser.parse("analyzer --option value");

        // Проверяем, что парсинг прошел корректно
        assertNotNull(result);
        assertFalse(result.isEmpty());
        // Дополнительные проверки...
    }


    @Test
    void testParseValidCommandWithOptionAndArgument() {


        Map<Command, List<ParameterWithArg>> result = parser.parse("analyzer --option value");

        assertEquals(1, result.size());
        assertTrue(result.containsKey(new AnalyzerCommand()));

        List<ParameterWithArg> params = result.get(new AnalyzerCommand());
        assertEquals(1, params.size());
        assertEquals("--option", params.get(0).options());
        assertEquals("value", params.get(0).arg());
    }

    @Test
    void testParseUnknownCommand() {

        Map<Command, List<ParameterWithArg>> result = parser.parse("unknowncmd --option value");

        assertEquals(1, result.size());
        assertEquals(0, result.getOrDefault(new UnknownCommand(),new ArrayList<>()).size());
    }

    @Test
    void testParseCommandWithMultipleOptionsAndArguments() {

        Map<Command, List<ParameterWithArg>> result = parser.parse("analyzer --option1 arg1 --option2 arg2");

        assertTrue(result.containsKey(new AnalyzerCommand()));

        List<ParameterWithArg> params = result.get(new AnalyzerCommand());
        assertEquals(2, params.size());
        assertEquals("--option1", params.get(0).options());
        assertEquals("arg1", params.get(0).arg());
        assertEquals("--option2", params.get(1).options());
        assertEquals("arg2", params.get(1).arg());
    }

    @Test
    void testGetKeyByValue() {
        Map<Command, List<ParameterWithArg>> map = Map.of(new AnalyzerCommand(), new ArrayList<>());
        Command command = CommandParser.getKeyByValue(map, new ArrayList<>());

        assertEquals(new AnalyzerCommand(), command);
    }


}

