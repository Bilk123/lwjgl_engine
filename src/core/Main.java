package core;

import graphics.Camera;
import graphics.Shader;
import input.Input;
import math.Vec2;
import math.Vec3;

public class Main {


    public static void main(String[] args) {
        Window w = new Window(1600, 900, "Yo");

        Shader shader = new Shader("shaders/VertexShader.glsl", "shaders/FragmentShader.glsl");
        shader.enable();
        Camera camera = new Camera(Camera.CAMERA_PERSPECTIVE);
        
        while (!w.close()) {
            camera.input();
            w.clear();
            Vec2 sp = Input.mouseScreenCoords;

            shader.setUniformMat4("camTransMat", camera.getTransform());
            shader.setUniformVec3("lightPos", new Vec3(sp.x / 100f, 9f - sp.y / 100f, 0));


            w.update();
        }
        w.dispose();
    }


}
