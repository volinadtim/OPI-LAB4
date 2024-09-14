package my.web.area;

public class TargetAreaCircle implements TargetArea {
    public final String name = "Circle";
    private final float radiusScale;

    public TargetAreaCircle() {
        this.radiusScale = 1;
    }

    public TargetAreaCircle(float radiusScale) {
        this.radiusScale = radiusScale;
    }

    @Override
    public boolean contains(float x, float y, float radius) {
        var circleRadius = radius * radiusScale;
        return (x * x + y * y) <= circleRadius * circleRadius; // (x^2 + y^2) <= R^2
    }

    @Override
    public String getName() {
        return "Circle";
    }

    @Override
    public float getWidth() {
        return radiusScale;
    }

    @Override
    public float getHeight() {
        return radiusScale;
    }
}
