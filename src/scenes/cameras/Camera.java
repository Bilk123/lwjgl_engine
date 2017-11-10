package scenes.cameras;

import graphics.Transform;
import math.Mat4;
import math.Quat4;
import math.Vec3;

public abstract class Camera {
    private Mat4 projection;

    protected Transform transform;
    protected Quat4 rotation;

    float xAngle = 0;
    float yAngle = 0;

    protected Camera(Mat4 projection) {
        this.projection = projection;
        transform = new Transform();
    }

    public void input(float dt) {
    }

    void moveRotated(Vec3 displacement) {
        transform.translate(rotation.inverse().mul(displacement));
    }

    public Mat4 getTransform() {
        return transform.getMatrix();
    }

    public Mat4 getProjection() {
        return projection;
    }
}
