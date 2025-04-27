package my_project.tests.transformation;

import backend.academy.transformation.Transformation;
import backend.academy.transformation.TransformationDisc;
import backend.academy.transformation.TransformationEyefish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TransformationEyefishTest {
    private Transformation transformationDisc;

    @BeforeEach
    public void setUp() {
        transformationDisc = new TransformationEyefish();
    }

    @Test
    public void testOrigin() {
        double x = 0.0;
        double y = 0.0;

        double transformedX = transformationDisc.transformX(x, y);
        double transformedY = transformationDisc.transformY(x, y);

        assertEquals(0.0, transformedX, 0.0001);
        assertEquals(0.0, transformedY, 0.0001);
    }

    @Test
    public void testAlongYAxis() {
        double x1 = 0.0;
        double y1 = 1.0;

        double transformedX1 = transformationDisc.transformX(x1, y1);
        double transformedY1 = transformationDisc.transformY(x1, y1);

        assertEquals(0.0, transformedX1, 0.0001);
        assertNotEquals(0.0, transformedY1, 0.0001);

        double x2 = 0.0;
        double y2 = 2.0;

        double transformedX2 = transformationDisc.transformX(x2, y2);
        double transformedY2 = transformationDisc.transformY(x2, y2);

        assertEquals(0.0, transformedX2, 0.0001);
        assertNotEquals(0.0, transformedY2, 0.0001);
    }

    @Test
    public void testPointOneOne() {
        double x = 1.0;
        double y = 1.0;

        double transformedX = transformationDisc.transformX(x, y);
        double transformedY = transformationDisc.transformY(x, y);

        assertNotEquals(0.0, transformedX, 0.0001);
        assertNotEquals(0.0, transformedY, 0.0001);
    }

    @Test
    public void testPointTwoThree() {
        double x = 2.0;
        double y = 3.0;

        double transformedX = transformationDisc.transformX(x, y);
        double transformedY = transformationDisc.transformY(x, y);

        assertNotEquals(0.0, transformedX, 0.0001);
        assertNotEquals(0.0, transformedY, 0.0001);
    }


    @Test
    public void testPointAtZero() {
        double x = 0.0;
        double y = 0.0;

        double transformedX = transformationDisc.transformX(x, y);
        double transformedY = transformationDisc.transformY(x, y);

        assertEquals(0.0, transformedX, 0.0001);
        assertEquals(0.0, transformedY, 0.0001);
    }
}
