package graphics.G2D.shapes;

import graphics.G2D.renderables2D.Renderable2D;
import math.Vec2;

public abstract class Shape extends Renderable2D{

   public abstract float calculateArea();

   public abstract Vec2 calculateCentroid();

   public abstract boolean contains(Vec2 point);
}
