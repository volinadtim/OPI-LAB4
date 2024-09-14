package my.web.area;

public class TargetAreaTriangle implements TargetArea {
    public final String name = "Triangle";
    private final float widthScale;
    private final float heightScale;

    public TargetAreaTriangle() {
        this.widthScale = 1;
        this.heightScale = 1;
    }

    public TargetAreaTriangle(float sizeScale) {
        this.widthScale = sizeScale;
        this.heightScale = sizeScale;
    }

    public TargetAreaTriangle(float widthScale, float heightScale) {
        this.widthScale = widthScale;
        this.heightScale = heightScale;
    }

    @Override
    public boolean contains(float x, float y, float radius) {
        return x / widthScale + y / heightScale <= radius;
    }

    @Override
    public String getName() {
        return "Triangle";
    }

    @Override
    public float getWidth() {
        return widthScale;
    }

    @Override
    public float getHeight() {
        return heightScale;
    }
}
