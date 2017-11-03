package core;

import graphics.G2D.renderables2D.Sprite;
import input.Input;
import math.Vec4;
import scenes.Scene2D;
import scenes.entities.Entity;
import util.Time;

public class Main {

    public static void main(String[] args) {
        Window w = new Window(1600, 900, "Yo");
        Debug debug = new Debug(w);

        Scene2D scene = new Scene2D();
        Sprite sprite = new Sprite(0, 0, 1, 1, new Vec4(1, 1, 1, 1));
        scene.addEntity(new Entity(sprite));

        int frames = 0;
        long timer = System.currentTimeMillis();
        long lastTime = Time.getTime();
        final double ns = Time.SECOND / 60.0;
        double delta = 0;
        int updates = 0;

        while (!w.close()) {
            w.clear();

            long now = Time.getTime();
            delta += (now - lastTime) / ns;
            Time.setDeltaTime((float) (delta / 60.0));
            lastTime = now;
            while (delta >= 1) {
                Input.update();
                //scene.update(Time.deltaTime);
                debug.update(Time.deltaTime);
                delta--;
                updates++;
            }

            frames++;
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                debug.fps.setText("fps | " + frames + " || ups | " + updates);
                frames = 0;
                updates = 0;
            }

            //scene.render();
            debug.render();
            w.update();
        }
        w.dispose();
    }

    private void update() {

    }


}
