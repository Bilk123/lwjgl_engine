package scenes.cameras;


import input.Input;
import math.Mat4;
import math.Quat4;
import math.Vec3;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;

public class FirstPersonCamera extends Camera {

    public FirstPersonCamera(float fov) {
        super(Mat4.perspective(fov, 16.0f / 9.0f, 0.5f, 1000f));
    }

    @Override
    public void input(float dt) {
        rotation = new Quat4(new Vec3(1, 0, 0),(float) Math.toDegrees(yAngle)).mul(new Quat4(new Vec3(0, 1, 0), (float)Math.toDegrees(xAngle)));
        float speed = 2f;
        if (Input.isKeyDown(GLFW_KEY_RIGHT)) xAngle-=dt*speed;
        if (Input.isKeyDown(GLFW_KEY_LEFT)) xAngle+=dt*speed;
        if (Input.isKeyDown(GLFW_KEY_UP)) yAngle+=dt*speed;
        if (Input.isKeyDown(GLFW_KEY_DOWN)) yAngle-=dt*speed;
        if (Input.isKeyDown(GLFW_KEY_SPACE)) moveRotated(new Vec3(0, -speed*dt, 0));
        if (Input.isKeyDown(GLFW_KEY_C)) moveRotated(new Vec3(0, speed*dt, 0));
        if (Input.isKeyDown(GLFW_KEY_D)) moveRotated(new Vec3(-speed*dt, 0, 0));
        if (Input.isKeyDown(GLFW_KEY_A)) moveRotated(new Vec3(speed*dt, 0, 0));
        if (Input.isKeyDown(GLFW_KEY_W)) moveRotated(new Vec3(0, 0, speed*dt));
        if (Input.isKeyDown(GLFW_KEY_S)) moveRotated(new Vec3(0, 0, -speed*dt));

        if (Input.isButtonDown(GLFW_MOUSE_BUTTON_1)) {
            xAngle += Input.mouseScreenCoordsChange.x / 10f;
            yAngle += Input.mouseScreenCoordsChange.y / 10f;
        }
        transform.setRotation(rotation);
    }
}
