package graphics.G2D;

import math.Vec2;
import math.Vec3;
import math.Vec4;

public class Sprite extends Renderable2D {
    public Sprite(float x, float y, float width, float height, Vec4 color) {
        super(new Vec3(x, y, 0f), new Vec2(width, height), color);
        uvs.add(new Vec2(0, 0));
        uvs.add(new Vec2(0, 1));
        uvs.add(new Vec2(1, 1));
        uvs.add(new Vec2(1, 0));
    }
}
