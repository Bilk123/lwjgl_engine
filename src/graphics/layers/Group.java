package graphics.layers;

import graphics.G2D.Renderable2D;
import graphics.G2D.Renderer2D;
import math.Mat4;

import java.util.ArrayList;

public class Group extends Renderable2D {

    protected ArrayList<Renderable2D> children;

    public Group() {
        children = new ArrayList<>();
        transform = new Mat4(1);
    }

    public Group(Mat4 transform) {
        children = new ArrayList<>();
        this.transform = transform;
    }

    public void setTransform(Mat4 transform) {
        this.transform = transform;
    }

    public void addChild(Renderable2D renderable) {
        children.add(renderable);
    }

    @Override
    public void submit(Renderer2D renderer) {
        super.submit(renderer);
        for (Renderable2D renderable : children) {
            if (renderable instanceof Group) {
                renderable.submit(renderer);
            } else
                renderer.submit(renderable);
        }
        renderer.pop();
    }
}
