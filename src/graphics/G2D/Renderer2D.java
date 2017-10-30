package graphics.G2D;

public abstract class Renderer2D {

    public void begin() {
    }

    public void end() {
    }

    public abstract <T extends Renderable2D> void submit(T renderable);

    public abstract void flush();

}
