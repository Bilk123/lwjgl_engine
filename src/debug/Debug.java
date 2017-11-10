package debug;

import core.Window;
import graphics.G2D.shapes.Rectangle;
import graphics.Transform;
import graphics.font.Font;
import graphics.font.Text;
import graphics.layers.TextLayer;
import math.Quat4;
import math.Vec2;
import math.Vec3;
import math.Vec4;
import physics.physics2D.Collision;
import physics.physics2D.Contact;
import physics.physics2D.RigidBody2D;

public class Debug {

    private TextLayer debugText;
    private DebugLayer debugDrawTools;
    public Text fps;
    Rectangle r1, r2, r3;

    RigidBody2D rba, rbb, rbc;
    Contact c, c2;

    public Debug(Window w) {
        debugText = new TextLayer(w);
        debugDrawTools = new DebugLayer();

        Font debugFont = new Font("fonts/arial32.fnt", "fonts/arial32.png", 32);
        fps = new Text("fps | ", debugFont, new Transform(new Vec3(5, 5, 0)), new Vec4(0.75f, 0.75f, 0.75f, 1f));
        debugText.addRenderable(fps);
        r1 = new Rectangle(new Vec2(0, -4), 20f, 1);
        r2 = new Rectangle(new Vec2(-5, 0.5f), 1, 1);
        r2.getTransform().rotate(new Quat4(Vec3.Z, 45));
        r3 = new Rectangle(new Vec2(5, 0f), 1, 1);

        rba = new RigidBody2D(10f, r1);
        rba.setFixed(true);
        rbb = new RigidBody2D(5f, r2);
        rbc = new RigidBody2D(5f, r3);
        rbc.setVelocity(new Vec2(0,-10f));

        c = new Contact(rba, rbc);
        debugDrawTools.addRenderable(rba.getShape());
        debugDrawTools.addRenderable(rbc.getShape());

    }


    public void render() {
        debugText.render();
        debugDrawTools.render();
    }

    public void update(float dt) {
        if (Collision.rectangleVsRectangle((Rectangle) rba.getShape(), (Rectangle) rbc.getShape(), c)) {
            c.resolveContact();
        }

        rba.update(dt);
        rbc.update(dt);
        debugText.update(dt);
        debugDrawTools.update(dt);
    }
}
