package math;

public class Mat2 {
    private float[] mat;

    public Mat2(float[] mat){
        this.mat = mat;
    }

    public Mat2() {
        mat = new float[4];
    }

    public Mat2(float diagonal) {
        mat = new float[4];
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 2; x++) {
                if (x == y) {
                    mat[x + y * 2] = diagonal;
                }
            }
        }
    }

    public Mat2 mul(Mat2 m) {
        Mat2 newMat = new Mat2();
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 2; x++) {
                float sum = 0.0f;
                for (int i = 0; i < 2; i++) {
                    sum += mat[x + i * 2] * m.mat[i + y * 2];
                }
                newMat.mat[x + y * 2] = sum;
            }
        }
        return newMat;
    }

    public Vec2 mul(Vec2 v) {
        return new Vec2(
                getElement(0, 0) * v.x + getElement(1, 0) * v.y,
                getElement(0, 1) * v.x + getElement(1, 1) * v.y);
    }

    public Mat2 inverse() {

        return new Mat2(new float[]{
                getElement(1, 1), -getElement(1, 0),
                -getElement(0, 1), getElement(0, 0)
        });
    }


    public Vec2 solve(Vec2 d) {
        return inverse().mul(d);
    }

    public float getElement(int x, int y) {
        return mat[x + y * 2];
    }

    public void setMat(float[] mat) {
        this.mat = mat;
    }
}
