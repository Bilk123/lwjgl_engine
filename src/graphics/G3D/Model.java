package graphics.G3D;

import graphics.Transform;

public class Model {
    private Mesh m;
    private Transform transform;

    public Model(Mesh m) {
        this.m = m;
    }

    public void setTransform(Transform transform) {
        this.transform = transform;
    }

    public Mesh getMesh() {
        return m;
    }
}
