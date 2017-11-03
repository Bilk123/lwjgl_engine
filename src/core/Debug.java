package core;

import graphics.G2D.shapes.Rectangle;
import graphics.Transform;
import graphics.font.Font;
import graphics.font.Text;
import graphics.layers.TextLayer;
import graphics.layers.DebugLayer;
import math.Vec2;
import math.Vec3;
import math.Vec4;
import physics.physics2D.RigidBody2D;

public class Debug {

    private TextLayer debugText;
    private DebugLayer debugDrawTools;
    public Text fps;
    RigidBody2D rb = new RigidBody2D(10f);

    public Debug(Window w) {
        debugText = new TextLayer(w);
        debugDrawTools = new DebugLayer();

        Font debugFont = new Font("fonts/arial32.fnt", "fonts/arial32.png", 32);
        fps = new Text("fps | ", debugFont, new Transform(new Vec3(5, 5, 0)), new Vec4(0.75f, 0.75f, 0.75f, 1f));
        debugText.addRenderable(fps);
        rb.addFixture(new Rectangle(new Vec2(0,0),1,1));
        debugDrawTools.addRenderable(rb);
    }

    public void render() {
        debugText.render();
        debugDrawTools.render();
    }

    public void update(float dt) {
        rb.update(dt);
        debugDrawTools.update(dt);
        debugText.update(dt);
    }
}
