package scenes.cameras;

import math.Mat4;

public class OrthographicCamera extends Camera {
    public OrthographicCamera(float l, float r, float b, float t, float n, float f, boolean flipY) {
        super(Mat4.orthographic(l, r, b, t, n, f, flipY));
    }
}
