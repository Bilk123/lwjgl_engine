package graphics;

import math.Mat4;
import math.Quat4;
import math.Vec3;

public class Transform {
    private Mat4 transform;
    private Mat4 translate;
    private Mat4 scale;
    private Mat4 rotation;


    public Transform() {
        translate = new Mat4(1);
        scale = new Mat4(1);
        rotation = new Quat4().toMat4();
        update();
    }

    public Transform(Vec3 translation) {
        this.translate = Mat4.initTranslation(translation);
        this.scale = new Mat4(1);
        rotation = new Quat4().toMat4();
        update();
    }

    public Transform(Quat4 rotation) {
        this.translate = new Mat4(1);
        this.scale = new Mat4(1);
        this.rotation = rotation.toMat4();
        update();
    }

    public Transform(Vec3 translation, Vec3 scale) {
        translate = Mat4.initTranslation(translation);
        this.scale = Mat4.initScale(scale);
        rotation = new Quat4().toMat4();
        update();
    }

    public Transform(Vec3 translation, Vec3 scale, Quat4 rotation) {
        this.translate = Mat4.initTranslation(translation);
        this.scale = Mat4.initScale(scale);
        this.rotation = rotation.toMat4();
        update();
    }


    public Mat4 getMatrix() {
        return transform;
    }

    public void translate(Vec3 translate) {
        this.translate = this.translate.mul(Mat4.initTranslation(translate));
        update();
    }

    public void setTranslation(Vec3 translate) {
        this.translate = Mat4.initTranslation(translate);
        update();
    }

    public void rotate(Quat4 rotation) {
        this.rotation = this.rotation.mul(rotation.toMat4());
        update();
    }

    public void setRotation(Quat4 rotation) {
        this.rotation = rotation.toMat4();
        update();
    }

    public void scale(Vec3 scale) {
        this.scale = this.scale.mul(Mat4.initScale(scale));
        update();
    }

    public void setScale(Vec3 scale) {
        this.scale = Mat4.initScale(scale);
        update();
    }

    private void update() {
        transform = scale.mul(rotation).mul(translate);
    }

    public Mat4 getInverse() {
        return transform.inverse();
    }

    public void transform(Mat4 transform) {
        this.transform = this.transform.mul(transform);
    }

    public void setTransform(Mat4 transform) {
        this.transform = transform;
    }

    public Mat4 getRotation() {
        return rotation;
    }

    public Mat4 getScale() {
        return scale;
    }

    public Mat4 getTranslate() {
        return translate;
    }
}
