package debug;

import graphics.G2D.renderables2D.Group2D;
import graphics.G2D.renderables2D.Renderable2D;
import graphics.G2D.renderers2D.ShapeRenderer;
import graphics.G2D.shapes.Shape;
import graphics.Shader;
import graphics.font.Text;
import graphics.layers.Layer2D;
import scenes.cameras.OrthographicCamera;

public class DebugLayer extends Layer2D {
    public DebugLayer() {
        super(new Shader("shaders/ShapeVertex.glsl", "shaders/ShapeFragment.glsl"),
                new OrthographicCamera(-8f, 8f, -4.5f, 4.5f, -1f, 1f, false),
                new ShapeRenderer());

    }

    @Override
    public void addRenderable(Renderable2D renderable) {
        if ((renderable instanceof Shape ||
                        renderable instanceof Group2D) &&
                !(renderable instanceof Text)) {

            renderables.add(renderable);
        }//TODO: add exceptions for invalid renderables
    }
}
