package graphics.G2D.shapes;

import graphics.Transform;
import math.Vec2;
import math.Vec3;
import math.Vec4;

public class Ellipse extends Shape {
    public static final int RESOLUTION = 30;
    public static final int[] INDICES;
    public static final Vec2[] VERTICES;

    static {
        VERTICES = new Vec2[RESOLUTION + 1];

        float x, y;
        VERTICES[0] = new Vec2(0, 0);
        for (int i = 1; i < RESOLUTION + 1; i++) {
            x = (float) Math.cos(Math.toRadians(i * 360f / RESOLUTION));
            y = (float) Math.sin(Math.toRadians(i * 360f / RESOLUTION));
            VERTICES[i] = new Vec2(x, y);
        }

        INDICES = new int[RESOLUTION * 3 + 3];
        INDICES[0] = 0;
        INDICES[1] = 0;
        INDICES[2] = 1;
        for (int i = 0, o = 3; i < RESOLUTION; i++, o += 3) {
            INDICES[o] = i + 1;
            INDICES[o + 1] = i + 1;
            INDICES[o + 2] = ((i + 1) % (RESOLUTION)) + 1;
        }
    }

    private Vec2 centre;
    protected float width;
    private float height;


    public Ellipse(Vec2 centre, float width, float height) {
        this.centre = centre;
        this.width = width;
        this.height = height;
        transform = new Transform();
        transform.translate(new Vec3(centre, 0));
        transform.setScale(new Vec3(width / 2f, height / 2f, 0));
        color = new Vec4(1, 1, 1, 1);
    }

    @Override
    public float calculateArea() {
        return (float) (Math.PI * width * height);
    }

    @Override
    public Vec2 calculateCentroid() {
        return centre;
    }

    @Override
    public boolean contains(Vec2 point) {
        return (point.x * point.x * height * height) + (point.y * point.y * width * width) <= (width * width * height * height);
    }

}
