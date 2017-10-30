package graphics.G2D;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Simple2DRenderer extends Renderer2D{


    private ArrayList<StaticSprite> sprites;

    public Simple2DRenderer() {
        sprites = new ArrayList<>();
    }

    public void submit(Renderable2D s) {
        if(s instanceof StaticSprite)
        sprites.add((StaticSprite)s);
        else System.out.println("invalid renderable added to simple renderer");
    }

    public void flush() {
        for (StaticSprite s : sprites) {
            s.getVertexArray().bind();
            s.getIndexArray().bind();
            glDrawElements(GL_TRIANGLES, s.getIndexArray().getCount(), GL_UNSIGNED_INT, NULL);
            s.getIndexArray().unbind();
            s.getVertexArray().unbind();
        }
        sprites.clear();
    }
}
