package graphics.layers;

import graphics.G2D.Renderable2D;
import graphics.Shader;
import graphics.cameras.Camera;

abstract class Layer{
    protected Shader shader;
    private Camera camera;

    Layer(Shader shader, Camera c) {
        this.shader = shader;
        this.camera = c;
        shader.enable();
        shader.setUniformMat4("prMat", camera.getProjection());
    }

    public void update(){
        shader.enable();
        camera.input();
        shader.setUniformMat4("vmMat", camera.getTransform());
    }

    public abstract void render();

    public abstract void add(Renderable2D renderer);

    public Camera getCamera() {
        return camera;
    }
}
