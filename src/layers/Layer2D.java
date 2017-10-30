package layers;

import graphics.G2D.Renderable2D;
import graphics.G2D.Renderer2D;
import graphics.Shader;
import math.Mat4;

import java.util.ArrayList;

public abstract class Layer2D extends Layer {
    private Renderer2D renderer;
    private ArrayList<Renderable2D> renderables;

    protected Layer2D(Shader shader, Mat4 projection, Renderer2D renderer) {
        super(shader, projection);
        this.renderer = renderer;
        renderables = new ArrayList<>();
    }

    public void add(Renderable2D renderable) {
        renderables.add(renderable);
    }

    public void render() {
        renderer.begin();
        for (Renderable2D r : renderables) {
            renderer.submit(r);
        }
        renderer.end();
        renderer.flush();
    }
}
