package math;

import com.sun.javafx.binding.StringFormatter;

@SuppressWarnings("ALL")
public class Mat3 {
    private float[] mat;
    private static final int SIZE = 3;

    public Mat3() {
        mat = new float[16];
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                mat[x + 4 * y] = 0.0f;
            }
        }
    }

    public Mat3(float diagonal) {
        mat = new float[16];
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (x == y)
                    mat[x + 4 * y] = diagonal;
                else
                    mat[x + 4 * y] = 0.0f;
            }
        }
    }

    public Mat3(float[] mat) {
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

    public Mat3 mul(Mat3 m) {
        Mat3 newMat = new Mat3();
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                float sum = 0.0f;
                for (int i = 0; i < SIZE; i++) {
                    sum += getElement(x, i) * m.getElement(i, y);
                }
                newMat.setElement(x, y, sum);
            }
        }
        return newMat;
    }

    public Mat3 mul(float scl) {
        Mat3 newMat = new Mat3();
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                newMat.setElement(x, y, scl * getElement(x, y));
            }
        }
        return newMat;
    }

    public Vec2 mul(Vec2 v) {
        return new Vec2(
                getElement(0, 0) * v.x + getElement(1, 0) * v.y + getElement(2, 0),
                getElement(0, 1) * v.x + getElement(1, 1) * v.y + getElement(2, 1));
    }

    public Vec3 mul(Vec3 v) {
        return new Vec3(
                getElement(0, 0) * v.x + getElement(1, 0) * v.y + getElement(2, 0) * v.z,
                getElement(0, 1) * v.x + getElement(1, 1) * v.y + getElement(2, 1) * v.z,
                getElement(0, 2) * v.x + getElement(1, 2) * v.y + getElement(2, 2) * v.z);
    }

    public Mat3 transpose() {
        Mat3 newMatrix = new Mat3();
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                newMatrix.setElement(x, y, getElement(y, x));
            }
        }
        return newMatrix;
    }


    public float determinant() {
        float det = 0;
        for (int i = 0; i < SIZE; i++)
            det += Math.pow(-1, i) * getElement(0, i)
                    * minor(0, i).determinant();
        return det;
    }

    public Mat2 minor(int row, int column) {
        Mat2 minor = new Mat2();
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; i != row && j < SIZE; j++)
                if (j != column)
                    minor.setElement(i < row ? i : i - 1, j < column ? j : j - 1, getElement(i, j));
        return (minor);
    }

    public Mat3 inverse() {
        Mat3 inverse = new Mat3();

        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                inverse.setElement(i, j, (float) Math.pow(-1, i + j)
                        * minor(i, j).determinant());

        float det = 1.0f / determinant();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j <= i; j++) {
                float temp = inverse.getElement(i,j);
                inverse.setElement(i,j,inverse.getElement(j,i)*det);
                inverse.setElement(j,i, temp*det);
            }
        }
        return inverse;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                sb.append(StringFormatter.format("%.1f",getElement(x, y)).getValue());
                if (x < SIZE-1) {
                    sb.append(", ");
                } else
                    sb.append("]\n");
            }
            if (y < SIZE-1) {
                sb.append("[");
            } else sb.append("\n");
        }
        return sb.toString();
    }

    public void setElement(int x, int y, float val) {
        if (x >= 0 && x < SIZE && y >= 0 && y < SIZE) {
            mat[x + SIZE * y] = val;
        }
    }

    public float getElement(int x, int y) {
        if (x >= 0 && x < SIZE && y >= 0 && y < SIZE) {
            return mat[x + SIZE * y];
        } else return 0.0f;
    }

    public void set(float[] mat) {
        this.mat = mat;
    }

}
