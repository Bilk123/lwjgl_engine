package graphics.layers;

import graphics.G2D.renderables2D.Renderable2D;
import graphics.Shader;
import graphics.cameras.Camera;

abstract class Layer {
    protected Shader shader;
    private Camera camera;

    Layer(Shader shader, Camera c) {
        this.shader = shader;
        this.camera = c;
        shader.enable();
        shader.setUniformMat4("prMat", camera.getProjection());
    }

    public void update(float dt) {
        shader.enable();
        camera.input(dt);
        shader.setUniformMat4("vmMat", camera.getTransform());
    }

    public abstract void render();

    public abstract void addRenderable(Renderable2D renderer);

    public Camera getCamera() {
        return camera;
    }

    public void delete() {
        shader.delete();
    }
}
