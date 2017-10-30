package graphics;

import input.Input;
import math.Mat4;
import math.Quat4;
import math.Vec3;

import static org.lwjgl.glfw.GLFW.*;

public class Camera {
    public static final int CAMERA_PERSPECTIVE = 0x1;
    public static final int CAMERA_ORTHOGONAL = 0x2;

    private Mat4 projection;
    private Vec3 translation = new Vec3();
    private Quat4 rotation = new Quat4();
    private float xAngle = 0;
    private float yAngle = 0;

    public Camera(int type) {
        switch (type) {
            case CAMERA_ORTHOGONAL:
                projection = Mat4.orthographic(0f, 16f, 0f, 9f, -7f, 7f);
                break;
            case CAMERA_PERSPECTIVE:
                projection = Mat4.perspective(60f, 16.0f / 9.0f, 0.5f, 1000f);
                break;
        }
    }

    public void input() {
        rotation = new Quat4(new Vec3(-1, 0, 0), yAngle).mul(new Quat4(new Vec3(0, -1, 0), xAngle));
        if (Input.isKeyDown(GLFW_KEY_RIGHT)) xAngle--;
        if (Input.isKeyDown(GLFW_KEY_LEFT)) xAngle++;
        if (Input.isKeyDown(GLFW_KEY_UP)) yAngle++;
        if (Input.isKeyDown(GLFW_KEY_DOWN)) yAngle--;
        float speed = 0.2f;
        if (Input.isKeyDown(GLFW_KEY_SPACE)) moveRotated(new Vec3(0, -speed, 0));
        if (Input.isKeyDown(GLFW_KEY_C)) moveRotated(new Vec3(0, speed, 0));
        if (Input.isKeyDown(GLFW_KEY_D)) moveRotated(new Vec3(-speed, 0, 0));
        if (Input.isKeyDown(GLFW_KEY_A)) moveRotated(new Vec3(speed, 0, 0));
        if (Input.isKeyDown(GLFW_KEY_W)) moveRotated(new Vec3(0, 0, speed));
        if (Input.isKeyDown(GLFW_KEY_S)) moveRotated(new Vec3(0, 0, -speed));

        if(Input.isButtonDown(GLFW_MOUSE_BUTTON_1)){
            xAngle+=Input.mouseScreenCoordsChange.x/10f;
            yAngle+=Input.mouseScreenCoordsChange.y/10f;
        }
    }

    private void moveRotated(Vec3 vec) {
        translation = translation.add(rotation.inverse().mul(vec));
    }

    public Mat4 getTransform() {
        return Transform.initTranslation(translation).mul(rotation.toMat4());
    }

    public Mat4 getProjection() {
        return projection;
    }
}
