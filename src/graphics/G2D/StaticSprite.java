package graphics.G2D;

import graphics.buffers.Buffer;
import graphics.buffers.IndexBuffer;
import graphics.buffers.VertexArray;
import math.Vec2;
import math.Vec3;
import math.Vec4;

public class StaticSprite extends Renderable2D {


    private VertexArray va;
    private IndexBuffer ia;

    public StaticSprite(float x, float y, float width, float height, Vec4 color) {
        super(new Vec3(x, y, 0), new Vec2(width, height), color);

        va = new VertexArray();
        float[] vertices = {
                x, y, 0,
                x, y + height, 0,
                x + width, y + height, 0,
                x + width, y, 0
        };

        float[] colors = {
                color.x, color.y, color.z, color.w,
                color.x, color.y, color.z, color.w,
                color.x, color.y, color.z, color.w,
                color.x, color.y, color.z, color.w
        };

        va.addBuffer(new Buffer(vertices, Vec3.COMPONENTS), 0);
        va.addBuffer(new Buffer(colors, Vec4.COMPONENTS), 1);
        int[] indices = {
                0, 1, 2, 2, 3, 0
        };
        ia = new IndexBuffer(indices);
    }


    VertexArray getVertexArray() {
        return va;
    }

    IndexBuffer getIndexArray() {
        return ia;
    }

}
