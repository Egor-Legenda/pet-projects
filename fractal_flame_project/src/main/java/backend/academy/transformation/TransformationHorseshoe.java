package backend.academy.transformation;

public class TransformationHorseshoe implements Transformation {
    @Override
    public double transformX(double x, double y) {
        double r = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        return ((x - y) * (x + y) / r);
    }

    @Override
    public double transformY(double x, double y) {
        double r = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        return (2 * x * y / r);
    }
}
