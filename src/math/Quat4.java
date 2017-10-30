package math;


public class Quat4 {
    private float w;
    private Vec3 v;

    public Quat4(Vec3 n, float a) {
        a = (float) Math.toRadians(a);
        w = (float) Math.cos(a / 2);
        v = n.mul((float) Math.sin(a / 2));
    }

    public Quat4() {
        w = 0;
        v = new Vec3();
    }

    public float len2() {
        return w * w + v.dot(v);
    }

    public float len() {
        return (float) Math.sqrt(len2());
    }

    public Quat4 normailise() {
        float len = len();
        w /= len;
        v = v.mul(1 / len);
        return this;
    }

    public Quat4 mul(Quat4 q) {
        /*(Wa*Wb - Vb*Va, Wb * Va + Wa * Vb + Va x Vb)*/
        Quat4 q1 = new Quat4();
        q1.w = w * q.w - v.dot(q.v);
        q1.v = ((v.mul(q.w)).add(q.v.mul(w))).add(v.cross(q.v));
        return q1;
    }


    public Mat4 toMat4() {
        Mat4 newMat = new Mat4();
        newMat.set(new float[]{
                1 - 2 * v.z * v.z - 2 * v.y * v.y, -2 * v.z * w + 2 * v.y * v.x, 2 * v.y * w + 2 * v.z * v.x, 0,
                2 * v.x * v.y + 2 * w * v.z, 1 - 2 * v.z * v.z - 2 * v.x * v.x, 2 * v.z * v.y - 2 * v.x * w, 0,
                2 * v.x * v.z - 2 * w * v.y, 2 * v.y * v.z + 2 * w * v.x, 1 - 2 * v.y * v.y - 2 * v.x * v.x, 0,
                0, 0, 0, 1}
        );
        return newMat;
    }

    public Vec3 mul(Vec3 V) {
        Quat4 p = new Quat4();
        p.w = 0;
        p.v = V;
        Vec3 vcV = v.cross(V);
        return (V.add((vcV.mul(2 * w)))).add(((v.cross(vcV)).mul(2)));
    }

    public Quat4 inverse() {
        Quat4 q = new Quat4();
        q.w = w;
        q.v = v.mul(-1);
        return q;
    }

    @Override
    public String toString() {
        return "[" + w + ", " + v.x + ", " + v.y + ", " + v.z + "]";
    }
}
