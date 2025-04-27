package backend.academy.transformation;

public class TransformationHyperbolic implements Transformation {
    @Override
    public double transformX(double x, double y) {
        double r = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        double theta = Math.atan2(y, x);
        return Math.sin(theta) / r;
    }

    @Override
    public double transformY(double x, double y) {
        double r = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        double theta = Math.atan2(y, x);
        return r * Math.cos(theta);
    }
}
