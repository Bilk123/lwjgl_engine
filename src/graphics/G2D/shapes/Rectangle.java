package graphics.G2D.shapes;

import graphics.Transform;
import math.Vec2;
import math.Vec3;
import math.Vec4;

public class Rectangle extends Shape {

    public static final int[] indices = {0, 1, 2, 2, 3, 0};
    public static final Vec2[] VERTICES = {
            new Vec2(1, 1),
            new Vec2(-1, 1),
            new Vec2(-1, -1),
            new Vec2(1, -1),
    };

    private Vec2 center;
    private float width;
    private float height;

    public Rectangle(Vec2 center, float width, float height) {
        this.center = center;
        this.width = width;
        this.height = height;
        transform = new Transform();
        transform.setScale(new Vec3(width / 2, height / 2, 1));
        transform.setTranslation(new Vec3(center, 0));
        color = new Vec4(1, 1, 1, 1);


    }

    public Vec2 getMin() {
        Vec3 c1 = transform.getMatrix().mul(new Vec3(-1, -1, 0));
        Vec3 c2 = transform.getMatrix().mul(new Vec3(1, 1, 0));
        float minX = Math.min(c1.x, c2.x);
        float minY = Math.min(c1.y, c2.y);
        return new Vec2(minX, minY);
    }

    public Vec2 getMax() {
        Vec3 c1 = transform.getMatrix().mul(new Vec3(-1, -1, 0));
        Vec3 c2 = transform.getMatrix().mul(new Vec3(1, 1, 0));
        float maxX = Math.max(c1.x, c2.x);
        float maxY = Math.max(c1.y, c2.y);
        return new Vec2(maxX, maxY);
    }

    public Vec2[] getCorners() {
        Vec2[] temp = new Vec2[4];
        for (int i = 0; i < VERTICES.length; i++) {
            temp[i] = transform.getMatrix().mul(new Vec3(VERTICES[i], 0)).toVec2();
        }
        return temp;
    }

    @Override
    public float calculateArea() {
        return width * height;
    }

    @Override
    public Vec2 calculateCentroid() {
        return center;
    }

    @Override
    public boolean contains(Vec2 point) {
        return true;
    }

}
