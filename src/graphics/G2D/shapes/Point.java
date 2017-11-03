package graphics.G2D.shapes;

import graphics.Transform;
import math.Vec2;

public class Point extends Shape {
    public static final int[] INDICES = {0, 0, 1};

    private Vec2 point;


    public Point(Vec2 point) {
        this.point = point;
        transform = new Transform();
    }

    @Override
    public float calculateArea() {
        return 0;
    }

    @Override
    public Vec2 calculateCentroid() {
        return point;
    }

    @Override
    public boolean contains(Vec2 point) {
        return this.point.equals(point);
    }

    public Vec2 getPoint() {
        return point;
    }
}
