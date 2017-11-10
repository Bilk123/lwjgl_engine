package core;

import graphics.Transform;
import math.Quat4;
import math.Vec3;

public class TestMain {
    public static void main(String[] args) {
        Transform test = new Transform(new Vec3(2, 4, 5), new Vec3(2, 7, 8), new Quat4(Vec3.Z, 45));
        System.out.println(test.getMatrix().mul(test.getInverse()));
        //System.out.println(test.getMatrix().mul(test.getInverse().getMatrix()));
    }
}
