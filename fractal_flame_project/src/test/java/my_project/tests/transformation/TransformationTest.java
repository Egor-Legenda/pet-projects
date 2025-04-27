package my_project.tests.transformation;

import backend.academy.transformation.Transformation;
import backend.academy.transformation.TransformationDisc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TransformationTest {

    private Transformation translation;

    @BeforeEach
    public void setUp() {

        translation = new TransformationDisc();
    }

    @Test
    public void testTranslationTransformation() {
        double x = 1.0;
        double y = 1.0;

        double transformedX = translation.transformX(x, y);
        double transformedY = translation.transformY(x, y);

        assertEquals(-0.24097563321246931, transformedX, 0.0001);
        assertEquals(-0.06656383551035391, transformedY, 0.0001);
    }

}

