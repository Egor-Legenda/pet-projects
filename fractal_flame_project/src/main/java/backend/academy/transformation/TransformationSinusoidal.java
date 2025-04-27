package backend.academy.transformation;

public class TransformationSinusoidal implements Transformation {
    @Override
    public double transformX(double x, double y) {
        return Math.sin(x);
    }

    @Override
    public double transformY(double x, double y) {
        return Math.sin(y);
    }
}
