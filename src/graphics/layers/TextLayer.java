package graphics.layers;

import core.Window;
import graphics.G2D.renderables2D.Renderable2D;
import graphics.G2D.renderers2D.SpriteBatch;
import graphics.Shader;
import graphics.cameras.OrthographicCamera;
import graphics.font.Text;

public class TextLayer extends Layer2D {
    public TextLayer(Window window) {
        super(
                new Shader("shaders/VertexShader.glsl", "shaders/TextFragment.glsl"),
                new OrthographicCamera(0, window.getWidth(), 0, window.getHeight(), -1, 1, true),
                new SpriteBatch());
    }

    @Override
    public void addRenderable(Renderable2D renderable) {
        if (renderable instanceof Text)
            super.addRenderable(renderable);
    }
}
