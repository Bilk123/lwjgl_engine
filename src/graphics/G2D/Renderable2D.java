package graphics.G2D;

import math.Vec2;
import math.Vec3;
import math.Vec4;

public abstract class Renderable2D {
    private Vec3 position;
    private Vec4 color;
    private Vec2 size;

    Renderable2D(Vec3 position, Vec2 size, Vec4 color) {
        this.size = size;
        this.position = position;
        this.color = color;
    }

    Vec3 getPosition() {
        return position;
    }

    Vec2 getSize() {
        return size;
    }

    Vec4 getColor() {
        return color;
    }
}
