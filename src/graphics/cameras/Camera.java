package graphics.cameras;

import math.Mat4;
import math.Quat4;
import math.Vec3;

public abstract class Camera {

   private Mat4 projection;
   private Vec3 translation = new Vec3();

   Quat4 rotation = new Quat4();
   float xAngle = 0;
   float yAngle = 0;

    Camera(Mat4 projection){
        this.projection=projection;
    }

    public void input(float dt) {
    }

    void moveRotated(Vec3 displacement) {
        translation = translation.add(rotation.inverse().mul(displacement));
    }

    public Mat4 getTransform() {
        return Mat4.initTranslation(translation).mul(rotation.toMat4());
    }

    public Mat4 getProjection() {
        return projection;
    }
}
