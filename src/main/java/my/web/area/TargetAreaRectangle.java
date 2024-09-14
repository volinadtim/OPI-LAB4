package my.web.area;

public class TargetAreaRectangle implements TargetArea {
    public final String name = "Rectangle";
    private final float widthScale;
    private final float heightScale;

    public TargetAreaRectangle() {
        this.widthScale = 1;
        this.heightScale = 1;
    }

    public TargetAreaRectangle(float sizeScale) {
        this.widthScale = sizeScale;
        this.heightScale = sizeScale;
    }

    public TargetAreaRectangle(float widthScale, float heightScale) {
        this.widthScale = widthScale;
        this.heightScale = heightScale;
    }

    @Override
    public boolean contains(float x, float y, float radius) {
        var rectWidth = radius * widthScale;
        var rectHeight = radius * heightScale;
        return x <= rectWidth && y <= rectHeight;
    }

    @Override
    public String getName() {
        return "Rectangle";
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
