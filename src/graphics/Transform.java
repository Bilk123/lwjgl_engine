package graphics;

import math.Mat4;
import math.Quat4;
import math.Vec3;

public class Transform {
    public Mat4 tMat;
    public Mat4 rMat;
    public Mat4 sMat;

    public Transform(Vec3 translation, Vec3 scale, Quat4 rotation) {
        tMat = initTranslation(translation);
        sMat = initScale(scale);
        rMat = rotation.toMat4();

    }

    public static Mat4 initTranslation(Vec3 v) {
        Mat4 newMat = new Mat4(1);
        newMat.set(new float[]{
                1, 0, 0, v.x,
                0, 1, 0, v.y,
                0, 0, 1, v.z,
                0, 0, 0, 1
        });
        return newMat;
    }

    public static Mat4 initScale(Vec3 scale) {
        Mat4 newMat = new Mat4();
        newMat.set(new float[]{
                scale.x, 0, 0, 0,
                0, scale.y, 0, 0,
                0, 0, scale.z, 0,
                0, 0, 0, 1
        });
        return newMat;
    }
}
