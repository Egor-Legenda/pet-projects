package backend.academy.my_project.tests;

import backend.academy.my_project.IMechanism;
import backend.academy.my_project.Mechanism;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;

public class IMechanismTest {

    class TestIMechanism implements IMechanism {
        private boolean started = false;

        @Override
        public void start() throws InterruptedException {
            started = true;
        }
        public boolean isStarted() {
            return started;
        }
    }

    @Test
    public void testCreator(){
        IMechanism mechanism = new Mechanism();
        assertNotNull(mechanism);
        assertTrue(mechanism instanceof IMechanism);
    }

    @Test
    void testStartMethod() throws InterruptedException {
        TestIMechanism mechanism = new TestIMechanism();
        mechanism.start();
        assertTrue(mechanism.isStarted());
    }

    @Test
    void testStartThrowsException() throws InterruptedException {
        IMechanism mechanism = Mockito.mock(IMechanism.class);
        doThrow(new InterruptedException()).when(mechanism).start();
        assertThrows(InterruptedException.class, mechanism::start);
    }
}
