package graphics.G2D.shapes;

import math.Vec2;

public class Circle extends Ellipse {

    public Circle(Vec2 centre, float radius) {
        super(centre, radius * 2, radius * 2);
    }

    public float getRadius() {
        return width / 2;
    }
}
