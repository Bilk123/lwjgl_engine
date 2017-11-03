package physics.physics2D;

import graphics.G2D.renderables2D.Renderable2D;
import graphics.G2D.shapes.Shape;
import graphics.Transform;
import graphics.G2D.renderables2D.Group2D;
import math.Vec2;
import math.Vec3;

import java.util.ArrayList;

public class RigidBody2D extends Group2D {
    public static Vec2 gravity = new Vec2(0, 9.8f);

    private Vec2 com;
    private float mass;

    private Vec2 position;
    private Vec2 velocity;
    private Vec2 acceleration;


    public RigidBody2D(float mass) {
        this.mass = mass;
        transform = new Transform();
        position = new Vec2();
        velocity = new Vec2(1, 0);
        acceleration = new Vec2();
        children = new ArrayList<>();
    }

    @Override
    public void addChild(Renderable2D renderable) {
        if (renderable instanceof Shape)
            addFixture((Shape) renderable);
    }

    public void addFixture(Shape s) {
        children.add(s);
    }

    public void addForce(Vec2 force) {
    }

    public void addImpulse(Vec2 force, float time) {
    }

    public void getCentreOfMass() {
        float sumOfAreas = 0;
        float sumOfAreasWithY = 0;
        float sumOfAreasWithX = 0;
        float y, x;
        for (Renderable2D r : children) {
            Shape s = (Shape)r;
            sumOfAreas += s.calculateArea();
            sumOfAreasWithX += s.calculateArea() * s.calculateCentroid().x;
            sumOfAreasWithY += s.calculateArea() * s.calculateCentroid().y;
        }
        x = sumOfAreasWithX / sumOfAreas;
        y = sumOfAreasWithY / sumOfAreas;
        com = new Vec2(x, y);
    }

    public void setVelocity(Vec2 velocity) {
        this.velocity = velocity;
    }

    public void setAcceleration(Vec2 acceleration) {
        this.acceleration = acceleration;
    }

    public void setPosition(Vec2 position) {
        this.position = position;
    }

    public void update(float dt) {
        position = position.add(velocity.mul(dt));
        transform.setTranslation(new Vec3(position, 0));
    }
}
