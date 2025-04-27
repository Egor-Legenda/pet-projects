package my_project.tests.utilitsTests;

import backend.academy.utilits.ParameterWithArg;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParameterWithArgTest {

    @Test
    void testParameterWithArgInitialization() {
        ParameterWithArg param = new ParameterWithArg("option1", "argument1");

        assertEquals("option1", param.options());
        assertEquals("argument1", param.arg());
    }

    @Test
    void testSetArg() {
        ParameterWithArg param = new ParameterWithArg("option1", "argument1");

        param.arg("newArgument");
        assertEquals("newArgument", param.arg());
    }

    @Test
    void testToStringMethod() {
        ParameterWithArg param = new ParameterWithArg("option1", "argument1");

        String expectedToString = "ParameterWithArg(options=option1, arg=argument1)";
        assertEquals(expectedToString, param.toString());
    }
}
