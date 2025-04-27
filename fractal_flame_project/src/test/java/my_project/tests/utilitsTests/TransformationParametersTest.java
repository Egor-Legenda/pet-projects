package my_project.tests.utilitsTests;

import backend.academy.utilits.TransformationParameters;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransformationParametersTest {

    @Test
    void testConstructorAndGetters() {
        // Создаем объект с конкретными параметрами
        TransformationParameters parameters = new TransformationParameters(
            1.1, 2.2, 3.3, 4.4, 5.5, 6.6, 255, 128, 64
        );

        // Проверяем, что значения установлены правильно
        assertEquals(1.1, parameters.a(), 0.0001);
        assertEquals(2.2, parameters.b(), 0.0001);
        assertEquals(3.3, parameters.c(), 0.0001);
        assertEquals(4.4, parameters.d(), 0.0001);
        assertEquals(5.5, parameters.e(), 0.0001);
        assertEquals(6.6, parameters.f(), 0.0001);
        assertEquals(255, parameters.red());
        assertEquals(128, parameters.green());
        assertEquals(64, parameters.blue());
    }

    @Test
    void testSetters() {
        // Создаем объект с начальными параметрами
        TransformationParameters parameters = new TransformationParameters(
            0, 0, 0, 0, 0, 0, 0, 0, 0
        );

        // Устанавливаем новые значения через сеттеры
        parameters.a(7.7);
        parameters.b(8.8);
        parameters.c(9.9);
        parameters.d(10.10);
        parameters.e(11.11);
        parameters.f(12.12);
        parameters.red(100);
        parameters.green(150);
        parameters.blue(200);

        // Проверяем, что значения обновлены
        assertEquals(7.7, parameters.a(), 0.0001);
        assertEquals(8.8, parameters.b(), 0.0001);
        assertEquals(9.9, parameters.c(), 0.0001);
        assertEquals(10.10, parameters.d(), 0.0001);
        assertEquals(11.11, parameters.e(), 0.0001);
        assertEquals(12.12, parameters.f(), 0.0001);
        assertEquals(100, parameters.red());
        assertEquals(150, parameters.green());
        assertEquals(200, parameters.blue());
    }

    @Test
    void testBoundaryValues() {
        // Тестируем объект с граничными значениями
        TransformationParameters parameters = new TransformationParameters(
            Double.MAX_VALUE, Double.MIN_VALUE, 0, -Double.MAX_VALUE, Double.NaN, Double.POSITIVE_INFINITY,
            Integer.MAX_VALUE, Integer.MIN_VALUE, 0
        );

        // Проверяем значения
        assertEquals(Double.MAX_VALUE, parameters.a());
        assertEquals(Double.MIN_VALUE, parameters.b());
        assertEquals(0, parameters.c());
        assertEquals(-Double.MAX_VALUE, parameters.d());
        assertTrue(Double.isNaN(parameters.e()));
        assertTrue(Double.isInfinite(parameters.f()));
        assertEquals(Integer.MAX_VALUE, parameters.red());
        assertEquals(Integer.MIN_VALUE, parameters.green());
        assertEquals(0, parameters.blue());
    }
}

