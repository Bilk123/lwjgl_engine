package scenes.entities;

import graphics.G2D.renderables2D.Sprite;
import input.Input;
import math.Vec3;

import static org.lwjgl.glfw.GLFW.*;

public class Entity {
    private Sprite sprite;

    public Entity(Sprite sprite) {
        this.sprite = sprite;
    }

    public void update(float dt) {
        input(dt);
    }

    private void input(float dt) {
        if(Input.isKeyDown(GLFW_KEY_D)){
            sprite.getTransform().translate(new Vec3(dt,0,0));
        }
        if(Input.isKeyDown(GLFW_KEY_A)){
            sprite.getTransform().translate(new Vec3(-dt,0,0));
        }
        if(Input.isKeyDown(GLFW_KEY_W)){
            sprite.getTransform().translate(new Vec3(0,-dt,0));
        }
        if(Input.isKeyDown(GLFW_KEY_S)){
            sprite.getTransform().translate(new Vec3(0,dt,0));
        }
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void dispose() {

    }

}
