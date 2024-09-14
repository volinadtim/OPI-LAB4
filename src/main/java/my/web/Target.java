package my.web;

import my.web.area.TargetArea;

public class Target {
    private TargetArea quadrant1;
    private TargetArea quadrant2;
    private TargetArea quadrant3;
    private TargetArea quadrant4;

    public boolean containsCoordinate(float x, float y, float radius) {

        // I Quadrant
        if (x >= 0 && y >= 0) {
            if (quadrant1 == null) return false;
            return quadrant1.contains(x, y, radius);
        }

        // II Quadrant
        if (x <= 0 && y >= 0) {
            if (quadrant2 == null) return false;
            return quadrant2.contains(-x, y, radius);
        }

        // III Quadrant
        if (x <= 0 && y <= 0) {
            if (quadrant3 == null) return false;
            return quadrant3.contains(-x, -y, radius);
        }

        // IV Quadrant
        if (x >= 0 && y <= 0) {
            if (quadrant4 == null) return false;
            return quadrant4.contains(x, -y, radius);
        }

        return false;
    }

    public TargetArea getQuadrant1() {
        return quadrant1;
    }

    public TargetArea getQuadrant2() {
        return quadrant2;
    }

    public TargetArea getQuadrant3() {
        return quadrant3;
    }

    public TargetArea getQuadrant4() {
        return quadrant4;
    }

    public void setQuadrant1(TargetArea quadrant1) {
        this.quadrant1 = quadrant1;
    }

    public void setQuadrant2(TargetArea quadrant2) {
        this.quadrant2 = quadrant2;
    }

    public void setQuadrant3(TargetArea quadrant3) {
        this.quadrant3 = quadrant3;
    }

    public void setQuadrant4(TargetArea quadrant4) {
        this.quadrant4 = quadrant4;
    }
}
