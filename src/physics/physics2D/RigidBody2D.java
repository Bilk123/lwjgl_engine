package physics.physics2D;

import graphics.G2D.shapes.Shape;
import math.Vec2;
import math.Vec3;

public class RigidBody2D {
    private static Vec2 gravity = new Vec2(0f, 0f);

    public static Vec2 getGravity() {
        return gravity;
    }

    private Vec2 com;
    private float mass;

    private boolean fixed = false;
    private Vec2 position;
    private Vec2 velocity;
    private Vec2 acceleration;

    private Shape shape;

    public RigidBody2D(float mass, Shape shape) {
        this.mass = mass;
        this.shape = shape;
        position = shape.calculateCentroid();
        this.shape.getTransform().setTranslation(new Vec3(position, 0));
        velocity = new Vec2(0, 0);
        acceleration = new Vec2().add(gravity);
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public void addForce(Vec2 force) {
        acceleration = acceleration.add(force.div(mass));
    }

    public void getCentreOfMass() {
        com = shape.calculateCentroid();
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
        if (!fixed) {
            velocity = velocity.add(acceleration.mul(dt));
            position = position.add(velocity.mul(dt));
            shape.getTransform().setTranslation(new Vec3(position, 0));
        }
    }

    public static void setGravity(Vec2 gravity) {
        RigidBody2D.gravity = gravity;
    }

    public Vec2 getVelocity() {
        return velocity;
    }

    public Vec2 getPosition() {
        return position;
    }

    public void applyImpulse(Vec2 impulse) {
        velocity = velocity.add(impulse.div(mass));
    }

    public Shape getShape() {
        return shape;
    }

    public float getMass() {
        return mass;
    }

    public boolean isFixed() {
        return fixed;
    }
}
