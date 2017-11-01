package graphics.cameras;

import graphics.Transform;
import math.Mat4;
import math.Quat4;
import math.Vec3;

public abstract class Camera {

    protected Mat4 projection;
    protected Vec3 translation = new Vec3();
    protected Quat4 rotation = new Quat4();
    protected float xAngle = 0;
    protected float yAngle = 0;

    protected Camera(Mat4 projection){
        this.projection=projection;
    }

    public void input() {
    }

    protected void moveRotated(Vec3 displacement) {
        translation = translation.add(rotation.inverse().mul(displacement));
    }

    public Mat4 getTransform() {
        return Transform.initTranslation(translation).mul(rotation.toMat4());
    }

    public Mat4 getProjection() {
        return projection;
    }
}
