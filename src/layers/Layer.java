package layers;

import graphics.G2D.Renderable2D;
import graphics.Shader;
import math.Mat4;

abstract class  Layer{
    private Shader shader;
    private Mat4 projection;

    Layer(Shader shader, Mat4 projection) {
        this.shader = shader;
        this.projection = projection;
        shader.enable();
        shader.setUniformMat4("prMat", projection);
        shader.disable();
    }

    public abstract void add(Renderable2D renderer);
}
