package graphics.layers;

import graphics.G2D.SpriteBatch;
import graphics.Shader;
import graphics.cameras.FirstPersonCamera;

public class PerspectiveLayer extends Layer2D{

    public PerspectiveLayer(Shader shader) {
        super(shader, new FirstPersonCamera(30), new SpriteBatch());
    }


}
