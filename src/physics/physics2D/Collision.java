package physics.physics2D;

import graphics.G2D.shapes.Rectangle;
import math.Vec2;
import math.Vec3;

public class Collision {

    public static boolean rectangleVsRectangle(Rectangle r1, Rectangle r2, Contact c) {
        Vec2[] rect1 = r1.getCorners();
        Vec2 r1Cent = r1.getTransform().getMatrix().mul(Vec3.ZERO).toVec2();

        Vec2[] rect2 = r2.getCorners();
        Vec2 r2Cent = r2.getTransform().getMatrix().mul(Vec3.ZERO).toVec2();

        Vec2[] axis = new Vec2[4];
        axis[0] = getNormal(rect1[0], rect1[1]);
        axis[1] = getNormal(rect1[3], rect1[0]);
        axis[2] = getNormal(rect2[0], rect2[1]);
        axis[3] = getNormal(rect2[3], rect2[0]);

        Vec2 minAxis = new Vec2();
        float minOverlap = 0;
        for (int i = 0; i < 4; i++) {
            Projection p1 = new Projection(rect1, axis[i]);
            Projection p2 = new Projection(rect2, axis[i]);
            float c1 = r1Cent.dot(axis[i]);
            float c2 = r2Cent.dot(axis[i]);
            float cProjection = c2 - c1;

            if (!p1.overlaps(p2)) {
                c.setPenetration(0, new Vec2());
                return false;
            } else {
                if (minOverlap == 0) {
                    minOverlap = cProjection < 0 ? p1.getOverlap(p2) : -p1.getOverlap(p2);
                    minAxis = axis[i];
                } else {
                    float o = p1.getOverlap(p2);
                    if (o < Math.abs(minOverlap)) {
                        minOverlap = cProjection < 0 ? o : -o;
                        minAxis = axis[i];
                    }
                }

            }
        }
        c.setPenetration(minOverlap, minAxis);

        return true;
    }

    private static Vec2 getNormal(Vec2 p1, Vec2 p2) {
        Vec2 temp = p2.sub(p1).perp();
        if (temp.y < 0) temp.mul(-1);
        return temp;
    }

    private static class Projection {
        private float min, max;

        private Projection(Vec2[] shape, Vec2 axis) {
            min = shape[0].dot(axis);
            max = min;
            for (int i = 1; i < shape.length; i++) {
                float projection = shape[i].dot(axis);
                if (projection < min) min = projection;
                if (projection > max) max = projection;
            }
        }

        private boolean overlaps(Projection p) {
            return (min < p.max && p.min < max);
        }

        private float getOverlap(Projection p) {
            float overlap;
            if (max < p.max) {
                overlap = max - p.min;
            } else {
                overlap = p.max - min;
            }
            return overlap;
        }
    }


}
