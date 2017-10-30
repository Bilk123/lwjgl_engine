package graphics.G2D;

import math.Vec2;
import math.Vec3;
import math.Vec4;

public class Sprite extends Renderable2D {
    public Sprite(float x, float y, float width, float height, Vec4 color) {
        super(new Vec3(x, y, 0), new Vec2(width, height), color);
    }
}
