package backend.academy.transformation;

public class TransformationSwirl implements Transformation {
    @Override
    public double transformX(double x, double y) {
        double r = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        return (x * Math.sin(Math.pow(r, 2)) - y * Math.cos(Math.pow(r, 2)));
    }

    @Override
    public double transformY(double x, double y) {
        double r = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        return (x * Math.cos(Math.pow(r, 2)) + y * Math.sin(Math.pow(r, 2)));
    }
}
