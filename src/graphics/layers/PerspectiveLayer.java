package graphics.layers;

import graphics.G2D.renderers2D.SpriteBatch;
import graphics.Shader;
import scenes.cameras.FirstPersonCamera;

public class PerspectiveLayer extends Layer2D{

    public PerspectiveLayer(Shader shader) {
        super(shader, new FirstPersonCamera(30), new SpriteBatch());
    }


}
