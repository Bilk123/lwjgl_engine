package graphics.G2D;

import graphics.Texture;
import graphics.Transform;
import math.Mat4;
import math.Vec2;
import math.Vec3;
import math.Vec4;

import java.util.ArrayList;

public abstract class Renderable2D {
    protected Mat4 transform;
    protected ArrayList<Vec2> uvs;
    protected Vec4 color;
    protected Texture texture;

    protected Renderable2D() {
        transform = new Mat4(1);
        color = new Vec4(1, 1, 1, 1);
        uvs = new ArrayList<>();
    }

    protected Renderable2D(Vec3 position, Vec2 scale, Vec4 color) {
        transform = Transform.initScale(new Vec3(scale, 1)).mul(Transform.initTranslation(position));
        this.color = color;
        uvs = new ArrayList<>();

    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void submit(Renderer2D renderer) {
        renderer.push(transform);
    }

    public Vec4 getColor() {
        return color;
    }

    public ArrayList<Vec2> getUvs() {
        return uvs;
    }

    public int getTextureID() {
        return texture == null ? 0 : texture.getID();
    }

    public Mat4 getTransform() {
        return transform;
    }

    public void setColor(Vec4 color) {
        this.color = color;
    }

    public void setTransform(Mat4 transform) {
        this.transform = transform;
    }
}
