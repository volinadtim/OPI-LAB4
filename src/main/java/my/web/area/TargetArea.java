package my.web.area;

public interface TargetArea {
    String name = null;

    boolean contains(float x, float y, float radius);

    default String getName() {
        return name;
    }

    float getWidth();

    float getHeight();
}
