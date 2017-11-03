package graphics.G2D.shapes;

import graphics.Transform;
import math.Vec2;

public class Line extends Shape {
    public static final int[] INDICES = {0, 0, 1};

    private Vec2 p1, p2;

    public Line(Vec2 p1, Vec2 p2) {
        this.p1 = p1;
        this.p2 = p2;
        transform = new Transform();
    }

    public Vec2 getP1() {
        return p1;
    }

    public Vec2 getP2() {
        return p2;
    }


    @Override
    public float calculateArea() {
        return 0;
    }

    @Override
    public Vec2 calculateCentroid() {
        return p2.add(p1).mul(0.5f);
    }

    @Override
    public boolean contains(Vec2 point) {
        return false;
    }
}
