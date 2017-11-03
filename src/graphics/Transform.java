package graphics;

import math.Mat4;
import math.Quat4;
import math.Vec3;

public class Transform {
    private Mat4 transform;
    private Vec3 translate;
    private Vec3 scale;
    private Quat4 rotation;

    public Transform() {
        translate = new Vec3();
        scale = new Vec3(1, 1, 1);
        rotation = new Quat4();
        update();
    }

    public Transform(Vec3 translation) {
        this.translate = translation;
        rotation = new Quat4();
        scale = new Vec3(1, 1, 1);
        update();
    }

    public Transform(Quat4 rotation) {
        this.translate = new Vec3(0, 0, 0);
        this.scale = new Vec3(1, 1, 1);
        this.rotation = rotation;
        update();
    }

    public Transform(Vec3 translation, Vec3 scale) {
        translate = translation;
        this.scale = scale;
        rotation = new Quat4();
        update();
    }

    public Transform(Vec3 translation, Vec3 scale, Quat4 rotation) {
        this.translate = translation;
        this.scale = scale;
        this.rotation = rotation;
        update();
    }


    public Mat4 getMatrix() {
        return transform;
    }

    public void translate(Vec3 translate) {
        this.translate = this.translate.add(translate);
        update();
    }

    public void setTranslation(Vec3 translate) {
        this.translate = translate;
        update();
    }

    public void rotate(Quat4 rotation) {
        this.rotation = this.rotation.mul(rotation);
        update();
    }

    public void setRotation(Quat4 rotation) {
        this.rotation = rotation;
        update();
    }

    public void scale(Vec3 scale) {
        this.scale = this.scale.mul(scale);
        update();
    }

    public void setScale(Vec3 scale) {
        this.scale = scale;
        update();
    }

    private void update() {
        transform = Mat4.initScale(scale).mul(Mat4.initRotation(rotation)).mul(Mat4.initTranslation(translate));
    }

    public Quat4 getRotation() {
        return rotation;
    }

    public Vec3 getScale() {
        return scale;
    }

    public Vec3 getTranslate() {
        return translate;
    }
}
