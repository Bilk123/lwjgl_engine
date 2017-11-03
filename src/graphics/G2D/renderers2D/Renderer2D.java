package graphics.G2D.renderers2D;

import graphics.G2D.renderables2D.Renderable2D;
import math.Mat4;

import java.util.ArrayList;

public abstract class Renderer2D {
    private ArrayList<Mat4> transformationStack;
    protected Mat4 transformStackBack;

    protected Renderer2D() {
        transformationStack = new ArrayList<>();
        transformationStack.add(new Mat4(1));
        transformStackBack = getLastTransformMat();
    }

    public void push(Mat4 mat) {
        transformStackBack = transformStackBack.mul(mat);
        transformationStack.add(transformStackBack);
    }

    public void pushOverride(Mat4 mat) {
        transformationStack.add(mat);
        transformStackBack = mat;
    }

    public void pop() {
        if (transformationStack.size() > 1) {
            transformationStack.remove(transformationStack.size() - 1);
            transformStackBack = getLastTransformMat();
        }
    }

    public void transformationStackToIdentity() {
        transformationStack.clear();
        transformationStack.add(new Mat4(1));
        transformStackBack = transformationStack.get(0);
    }

    public void begin() {
    }

    public void end() {
    }

    public abstract void submit(Renderable2D renderable);

    public abstract void flush();

    protected Mat4 getLastTransformMat() {
        return transformationStack.get(transformationStack.size() - 1);
    }


}
