package math;

import com.sun.javafx.binding.StringFormatter;

@SuppressWarnings("ALL")
public class Mat2 {
    private float[] mat;
    private static final int SIZE = 2;

    public Mat2(float[] mat) {
        this.mat = new float[SIZE * SIZE];
        if (mat.length >= SIZE * SIZE)
            System.arraycopy(mat, 0, this.mat, 0, SIZE * SIZE);
        else {
            System.arraycopy(mat, 0, this.mat, 0, mat.length);
            for (int i = mat.length; i < 9; i++) {
                this.mat[i] = 0;
            }
        }
    }

    public Mat2() {
        mat = new float[SIZE * SIZE];
    }

    public Mat2(float diagonal) {
        mat = new float[SIZE * SIZE];
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                if (x == y) {
                    mat[x + y * SIZE] = diagonal;
                }
            }
        }
    }

    public Mat2 mul(Mat2 m) {
        Mat2 newMat = new Mat2();
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                float sum = 0.0f;
                for (int i = 0; i < SIZE; i++) {
                    sum += mat[x + i * SIZE] * m.mat[i + y * SIZE];
                }
                newMat.mat[x + y * SIZE] = sum;
            }
        }
        return newMat;
    }

    public Mat2 mul(float scl) {
        Mat2 newMat = new Mat2();
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                newMat.mat[x + SIZE * y] = scl * getElement(x, y);
            }
        }
        return newMat;
    }


    public Vec2 mul(Vec2 v) {
        return new Vec2(
                getElement(0, 0) * v.x + getElement(1, 0) * v.y,
                getElement(0, 1) * v.x + getElement(1, 1) * v.y);
    }

    public Mat2 transpose() {
        Mat2 newMatrix = new Mat2();
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                newMatrix.setElement(x, y, getElement(y, x));
            }
        }
        return newMatrix;
    }

    public float determinant() {
        return getElement(0, 0) * getElement(1, 1) - getElement(1, 0) * getElement(0, 1);
    }

    public Mat2 inverse() {
        float det = 1 / determinant();
        return new Mat2(new float[]{
                getElement(1, 1), -getElement(1, 0),
                -getElement(0, 1), getElement(0, 0)
        }).mul(det);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                sb.append(StringFormatter.format("%.1f",getElement(x, y)).getValue());
                if (x < SIZE - 1) {
                    sb.append(", ");
                } else
                    sb.append("]\n");
            }
            if (y < SIZE - 1) {
                sb.append("[");
            } else sb.append("\n");
        }
        return sb.toString();
    }

    public Vec2 solve(Vec2 d) {
        return inverse().mul(d);
    }

    public float getElement(int x, int y) {
        return mat[x + y * SIZE];
    }

    public void setMat(float[] mat) {
        this.mat = mat;
    }

    public void setElement(int x, int y, float val) {
        if (x >= 0 && x < SIZE && y >= 0 && y < SIZE) {
            mat[x + SIZE * y] = val;
        }
    }
}
