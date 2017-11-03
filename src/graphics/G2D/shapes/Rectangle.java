package graphics.G2D.shapes;

import graphics.Transform;
import math.Vec2;
import math.Vec3;
import math.Vec4;

public class Rectangle extends Shape {

    public static final int[] indices = {0, 1, 2, 2, 3, 0};
    public static Vec2[] vertices = {
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
        transform.setScale(new Vec3(width/2,height/2,1));
        transform.setTranslation(new Vec3(center,0));
        color = new Vec4(1, 1, 1, 1);

    }

    @Override
    public float calculateArea() {
        return width*height;
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
