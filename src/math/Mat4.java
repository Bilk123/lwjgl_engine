package math;

import com.sun.javafx.binding.StringFormatter;

@SuppressWarnings("ALL")
public class Mat4 {
    public static final Mat4 IDENTITY = new Mat4(1);
    public static final int SIZE = 4;
    public float[] mat;

    public Mat4() {
        mat = new float[SIZE * SIZE];
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                mat[x + SIZE * y] = 0.0f;
            }
        }
    }

    public Mat4(float diagonal) {
        mat = new float[16];
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                if (x == y)
                    mat[x + SIZE * y] = diagonal;
                else
                    mat[x + SIZE * y] = 0.0f;
            }
        }
    }

    public Mat4(float[] mat) {
        this.mat = new float[SIZE * SIZE];
        if (mat.length >= SIZE * SIZE)
            System.arraycopy(mat, 0, this.mat, 0, SIZE * SIZE);
        else {
            System.arraycopy(mat, 0, this.mat, 0, mat.length);
            for (int i = mat.length; i < SIZE * SIZE; i++) {
                this.mat[i] = 0;
            }
        }
    }

    public Mat4 mul(Mat4 m) {
        Mat4 newMat = new Mat4();
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

    public Mat4 mul(float scl) {
        Mat4 newMat = new Mat4();
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                newMat.setElement(x, y, scl * getElement(x, y));
            }
        }
        return newMat;
    }

    public Vec3 mul(Vec3 v) {
        return new Vec3(
                getElement(0, 0) * v.x + getElement(1, 0) * v.y + getElement(2, 0) * v.z + getElement(3, 0),
                getElement(0, 1) * v.x + getElement(1, 1) * v.y + getElement(2, 1) * v.z + getElement(3, 1),
                getElement(0, 2) * v.x + getElement(1, 2) * v.y + getElement(2, 2) * v.z + getElement(3, 2));
    }

    public Vec4 mul(Vec4 v) {
        return new Vec4(
                getElement(0, 0) * v.x + getElement(1, 0) * v.y + getElement(2, 0) * v.z + getElement(3, 0) * v.w,
                getElement(0, 1) * v.x + getElement(1, 1) * v.y + getElement(2, 1) * v.z + getElement(3, 1) * v.w,
                getElement(0, 2) * v.x + getElement(1, 2) * v.y + getElement(2, 2) * v.z + getElement(3, 2) * v.w,
                getElement(0, 3) * v.x + getElement(1, 3) * v.y + getElement(2, 3) * v.z + getElement(3, 3) * v.w);
    }

    public Mat4 transpose() {
        Mat4 newMatrix = new Mat4();
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

    public Mat3 minor(int row, int column) {
        Mat3 minor = new Mat3();
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; i != row && j < SIZE; j++)
                if (j != column)
                    minor.setElement(i < row ? i : i - 1, j < column ? j : j - 1, getElement(i, j));
        return minor;
    }

    public Mat4 inverse() {
        Mat4 inverse = new Mat4();

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

    public static Mat4 orthographic(float l, float r, float b, float t, float n, float f, boolean flipY) {
        return new Mat4(new float[]{
                2 / (r - l), 0, 0, -(r + l) / (r - l),
                0, (flipY ? -1 : 1) * 2 / (t - b), 0, (flipY ? 1 : -1) * (t + b) / (t - b),
                0, 0, -2 / (f - n), -(f + n) / (f - n),
                0, 0, 0, 1
        });
    }

    public static Mat4 perspective(float fov, float ar, float n, float f) {

        float t = (float) Math.tan(Math.toRadians(fov / 2)) * n;
        float b = -t;
        float r = t * ar;
        float l = -t * ar;
        return new Mat4(new float[]{
                2 * n / (r - l), 0, 0, 0,
                0, 2 * n / (t - b), 0, 0,
                (r + l) / (r - l), (t + b) / (t - b), -(f + n) / (f - n), -1,
                0, 0, -2 * f * n / (f - n), 0
        });
    }

    public static Mat4 initTranslation(Vec3 v) {
        return new Mat4(new float[]{
                1, 0, 0, v.x,
                0, 1, 0, v.y,
                0, 0, 1, v.z,
                0, 0, 0, 1
        });
    }

    public static Mat4 initScale(Vec3 scale) {
        return new Mat4(new float[]{
                scale.x, 0, 0, 0,
                0, scale.y, 0, 0,
                0, 0, scale.z, 0,
                0, 0, 0, 1
        });
    }

    public static Mat4 initRotation(Quat4 rotation) {
        return rotation.toMat4();
    }
}
