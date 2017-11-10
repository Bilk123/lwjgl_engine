package math;

@SuppressWarnings("ALL")
public class Vec2 {

    public static final Vec2 X = new Vec2(1, 0);
    public static final Vec2 Y = new Vec2(0, 1);

    public static final int COMPONENTS = 2;
    public static final int BYTES = COMPONENTS * Float.BYTES;

    public float x, y;

    public Vec2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vec2() {
        x = y = 0;
    }

    public Vec2 add(Vec2 v) {
        return new Vec2(x + v.x, y + v.y);
    }

    public Vec2 add(float vx, float vy) {
        return new Vec2(x + vx, y + vy);
    }

    public Vec2 sub(Vec2 v) {
        return new Vec2(x - v.x, y - v.y);
    }

    public Vec2 sub(float vx, float vy) {
        return new Vec2(x - vx, y - vy);
    }

    public Vec2 mul(Vec2 v) {
        return new Vec2(x * v.x, y * v.y);
    }

    public Vec2 mul(float sx, float sy) {
        return new Vec2(x * sx, y * sy);
    }

    public Vec2 mul(float scl) {
        return new Vec2(scl * x, scl * y);
    }

    public Vec2 div(Vec2 v) {
        return new Vec2(x / v.x, y / v.y);
    }

    public Vec2 div(float sx, float sy) {
        return new Vec2(x / sx, y / sy);

    }

    public Vec2 div(float scl) {
        return new Vec2(x / scl, y / scl);
    }

    public Vec2 rotate(float a) {
        float c = (float) Math.cos(Math.toRadians(a));
        float s = (float) Math.sin(Math.toRadians(a));
        return new Vec2(x * c - y * s, y * c + x * s);
    }

    public Vec2 normalise() {
        float len = len();
        x /= len;
        y /= len;
        return this;
    }

    public Vec2 cpy() {
        return new Vec2(x, y);
    }

    public float dot(Vec2 v) {
        return x * v.x + y * v.y;
    }

    public float len2() {
        return dot(this);
    }

    public float len() {
        return (float) Math.sqrt(len2());
    }

    public void set(Vec2 v) {
        x = v.x;
        y = v.y;
    }

    public Vec2 perp() {
        return new Vec2(y, -x);
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vec2) {
            Vec2 v = (Vec2) obj;
            return v.x == x && v.y == y;
        } else return false;
    }
}
