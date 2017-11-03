package scenes;

import graphics.G2D.renderers2D.SpriteBatch;
import graphics.Shader;
import graphics.cameras.OrthographicCamera;
import graphics.layers.Layer2D;
import scenes.entities.Entity;

import java.util.ArrayList;

public class Scene2D extends Layer2D {
    private ArrayList<Entity> entities;

    public Scene2D() {
        super(new Shader("shaders/VertexShader.glsl", "shaders/FragmentShader.glsl"), new OrthographicCamera(-8f, 8f, -4.5f, 4.5f, -1, 1, true), new SpriteBatch());
        entities = new ArrayList<>();
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        for(Entity e:entities){
            e.update(dt);
        }
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
        addRenderable(entity.getSprite());
    }

}
