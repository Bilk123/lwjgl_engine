package graphics.layers;

import graphics.G2D.SpriteBatch;
import graphics.Shader;
import graphics.cameras.OrthographicCamera;
import input.Input;
import math.Vec2;
import math.Vec3;

public class OrthographicLayer extends Layer2D{
    public OrthographicLayer(Shader shader) {
        super(shader, new OrthographicCamera(0,1600,0,900,-1,1, true), new SpriteBatch());
    }

    @Override
    public void update() {
        super.update();
        Vec2 sp = Input.mouseScreenCoords;
        shader.setUniformVec3("lightPos", new Vec3(sp.x *0.02f-16f, 9f-sp.y *0.02f, 0));
    }

}
