package graphics.layers;

import graphics.G2D.renderables2D.Group2D;
import graphics.G2D.renderables2D.Renderable2D;
import graphics.G2D.renderers2D.Renderer2D;
import graphics.Shader;
import scenes.cameras.Camera;

import java.util.ArrayList;

public abstract class Layer2D extends Layer {
    protected Renderer2D renderer;
    protected ArrayList<Renderable2D> renderables;

    protected Layer2D(Shader shader, Camera c, Renderer2D renderer) {
        super(shader, c);
        this.renderer = renderer;
        renderables = new ArrayList<>();
    }

    public void addRenderable(Renderable2D renderable) {
        renderables.add(renderable);
    }

    public void render() {
        renderer.begin();
        for (Renderable2D r : renderables) {
            if (r instanceof Group2D) {
                r.submit(renderer);
            } else
                renderer.submit(r);
        }
        renderer.end();
        shader.enable();
        renderer.flush();
    }
}
