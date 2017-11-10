package graphics.G2D.renderables2D;

import graphics.G2D.renderers2D.Renderer2D;
import graphics.Transform;

import java.util.ArrayList;

public class Group2D extends Renderable2D {

    protected ArrayList<Renderable2D> children;

    public Group2D() {
        children = new ArrayList<>();
        transform = new Transform();
    }

    public Group2D(Transform transform) {
        children = new ArrayList<>();
        this.transform = transform;
    }

    public ArrayList<Renderable2D> getChildren() {
        return children;
    }

    public void setTransform(Transform transform) {
        this.transform = transform;
    }

    public void addChild(Renderable2D renderable) {
        children.add(renderable);
    }

    @Override
    public void submit(Renderer2D renderer) {
        super.submit(renderer);
        for (Renderable2D renderable : children) {
            if (renderable instanceof Group2D) {
                renderable.submit(renderer);
            } else
                renderer.submit(renderable);
        }
        renderer.pop();
    }
}
