package core;

import graphics.Transform;
import graphics.font.Font;
import graphics.font.Text;
import graphics.font.TextLayer;
import math.Vec3;
import math.Vec4;

public class Main {

    public static void main(String[] args) {
        Window w = new Window(1600, 900, "Yo");
        Font arial = new Font("fonts/arial.fnt", "fonts/arial.png",32);
        Text text = new Text("11",arial,Transform.initTranslation(new Vec3(800,450,0)), new Vec4(1,1,1,1));
        TextLayer textLayer = new TextLayer(w);
        textLayer.add(text);
        int frames = 0;
        long timer = System.currentTimeMillis();
        while (!w.close()) {
            w.clear();
            textLayer.render();
            frames++;
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("fps : " + frames);
                frames = 0;
            }

            w.update();
        }
        w.dispose();
    }


}
