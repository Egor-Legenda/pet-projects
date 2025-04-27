package backend.academy.transformation;

public class TransformationDisc implements Transformation {
    @Override
    public double transformX(double x, double y) {
        double r = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        double theta = Math.atan2(y, x);
        return theta * Math.sin(Math.PI * r) / Math.PI;
    }

    @Override
    public double transformY(double x, double y) {
        double r = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        double theta = Math.atan2(y, x);
        return theta * Math.cos(Math.PI * r) / Math.PI;
    }
}
