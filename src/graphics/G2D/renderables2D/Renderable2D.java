package graphics.G2D.renderables2D;

import graphics.G2D.renderers2D.Renderer2D;
import graphics.Texture;
import graphics.Transform;
import math.Vec2;
import math.Vec3;
import math.Vec4;

import java.util.ArrayList;

public abstract class Renderable2D {
    protected Transform transform;
    protected ArrayList<Vec2> uvs;
    protected Vec4 color;
    protected Texture texture;

    protected Renderable2D() {
        transform = new Transform();
        color = new Vec4(1, 1, 1, 1);
        uvs = new ArrayList<>();
    }

    protected Renderable2D(Vec3 position, Vec2 scale, Vec4 color) {
        transform = new Transform(position, new Vec3(scale, 1));
        this.color = color;
        uvs = new ArrayList<>();
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void submit(Renderer2D renderer) {
        renderer.push(transform.getMatrix());
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

    public Transform getTransform() {
        return transform;
    }

    public void setColor(Vec4 color) {
        this.color = color;
    }

    public void setTransform(Transform transform) {
        this.transform = transform;
    }
}
