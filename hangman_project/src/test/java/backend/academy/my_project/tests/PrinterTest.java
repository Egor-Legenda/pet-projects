package backend.academy.my_project.tests;

import backend.academy.my_project.Printer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PrinterTest {
    TestPrinter testPrinter;
    class TestPrinter implements Printer {
        private static boolean isPrint = false;
        private static boolean isPrintln = false;

        @Override
        public void print(String str){
            isPrint = true;
        }

        @Override
        public void println(String str){
            isPrintln = true;
        }

        public boolean isIsPrint(){
            return isPrint;
        }

        public boolean isIsPrintln() {
            return isPrintln;
        }
    }

    @Test
    public void testMethod(){
        testPrinter = new TestPrinter();
        testPrinter.print("Проверка print");
        assertTrue(testPrinter.isIsPrint(),"Ошибка");
        testPrinter.println("Проверка println");
        assertTrue(testPrinter.isIsPrintln(),"Ошибка");
    }

    @Test
    public void testConstructor(){
        Printer printer = new TestPrinter();
        assert(printer instanceof Printer);
    }
}
