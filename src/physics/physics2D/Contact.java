package physics.physics2D;

import math.Vec2;

public class Contact {
    private RigidBody2D rba, rbb;
    private float penetration = 0;
    private Vec2 normal = new Vec2();

    public Contact(RigidBody2D rba, RigidBody2D rbb) {
        this.rba = rba;
        this.rbb = rbb;
    }

    public void setPenetration(float penetration, Vec2 normal) {
        this.penetration = penetration;
        this.normal = normal;
    }

    public void correctPosition() {
        float r;

        if (rba.isFixed()) {
            r = 1;
        } else if (rbb.isFixed()) {
            r = 0;
        } else {
            r = rba.getMass() / (rba.getMass() + rbb.getMass());
        }
        rba.setPosition(rba.getPosition().add(normal.mul(penetration * (1 - r))));
        rbb.setPosition(rbb.getPosition().add(normal.mul(penetration * -r)));
    }

    public void resolveContact() {
        correctPosition();
        Vec2 rv = rbb.getVelocity().sub(rba.getVelocity());
        float contactVel = rv.dot(normal);
        float e = 0.75f;
        float j = -(1 + e) * contactVel;
        j /= ((1 / rba.getMass()) + (1 / rbb.getMass()));
        Vec2 impulse = normal.mul(j);
        if (!rba.isFixed())
            rba.applyImpulse(impulse.mul(-1));
        if (!rbb.isFixed())
            rbb.applyImpulse(impulse);

    }

    @Override
    public String toString() {
        return "normal:" + normal + " depth: " + penetration;
    }
}
