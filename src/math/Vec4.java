package math;

public class Vec4 {

    public static final int COMPONENTS = 4;
    public static final int BYTES = COMPONENTS * Float.BYTES;

    public float x, y, z, w;

    public Vec4(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;

    }

    public Vec4() {
        x = y = z = w = 0;
    }

    public Vec4 add(Vec4 v) {
        return new Vec4(x + v.x, y + v.y, z + v.z, w + v.w);
    }

    public Vec4 sub(Vec4 v) {
        return new Vec4(x - v.x, y - v.y, z - v.z, w - v.w);
    }

    public Vec4 mul(Vec4 v) {
        return new Vec4(x * v.x, y * v.y, z * v.z, w * v.w);
    }

    public Vec4 mul(float scl) {
        return new Vec4(scl * x, scl * y, scl * z, scl * w);
    }

    public Vec4 div(Vec4 v) {
        return new Vec4(x / v.x, y / v.y, z / v.z, w / v.w);
    }

    public Vec4 div(float scl) {
        return new Vec4(x / scl, y / scl, z / scl, w / scl);
    }

    public float dot(Vec4 v) {
        return x * v.x + y * v.y + z * v.z + w * v.w;
    }

    public float len2() {
        return dot(this);
    }

    public float len() {
        return (float) Math.sqrt(len2());
    }

    public Vec4 normalise() {
        float len = len();
        x /= len;
        y /= len;
        z /= len;
        w /= len;
        return this;
    }

    public Vec4 cpy() {
        return new Vec4(x, y, z, w);
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + ", " + z + ", " + w + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vec4) {
            Vec4 v = (Vec4) obj;
            return v.x == x && v.y == y && v.z == z && v.w == w;
        } else return false;
    }
}
