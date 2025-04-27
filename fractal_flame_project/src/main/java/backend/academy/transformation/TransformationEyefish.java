package backend.academy.transformation;

public class TransformationEyefish implements Transformation {
    @Override
    public double transformX(double x, double y) {
        double r = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        return 2 * x / (r + 1);
    }

    @Override
    public double transformY(double x, double y) {
        double r = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        return 2 * y / (r + 1);
    }
}
