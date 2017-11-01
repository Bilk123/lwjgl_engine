package math;

public class Vec3 {
    public static final int COMPONENTS = 3;
    public static final int BYTES = COMPONENTS * Float.BYTES;

    public float x, y, z;

    public Vec3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;

    }

    public Vec3(Vec2 v, float z) {
        x = v.x;
        y = v.y;
        this.z = z;
    }

    public Vec3() {
        x = y = z = 0;
    }

    public Vec3 add(Vec3 v) {
        return new Vec3(x + v.x, y + v.y, z + v.z);
    }

    public Vec3 add(float vx, float vy, float vz) {
        return new Vec3(x + vx, y + vy, z + vz);
    }

    public Vec3 sub(Vec3 v) {
        return new Vec3(x - v.x, y - v.y, z - v.z);
    }

    public Vec3 mul(Vec3 v) {
        return new Vec3(x * v.x, y * v.y, z * v.z);
    }

    public Vec3 mul(float scl) {
        return new Vec3(scl * x, scl * y, scl * z);
    }

    public Vec3 div(Vec3 v) {
        return new Vec3(x / v.x, y / v.y, z / v.z);
    }

    public Vec3 div(float scl) {
        return new Vec3(x / scl, y / scl, z / scl);
    }

    public float dot(Vec3 v) {
        return x * v.x + y * v.y + z * v.z;
    }

    public float len2() {
        return dot(this);
    }

    public float len() {
        return (float) Math.sqrt(len2());
    }

    public Vec3 cross(Vec3 vec) {
        return new Vec3(
                y * vec.z - z * vec.y,
                -x * vec.z + z * vec.x,
                x * vec.y - y * vec.x);
    }

    public Vec3 normalise() {
        float len = len();
        x /= len;
        y /= len;
        z /= len;
        return this;
    }

    public Vec3 cpy() {
        return new Vec3(x, y, z);
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + ", " + z + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vec3) {
            Vec3 v = (Vec3) obj;
            return v.x == x && v.y == y && v.z == z;
        } else return false;
    }
}
