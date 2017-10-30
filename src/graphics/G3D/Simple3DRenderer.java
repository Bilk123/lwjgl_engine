package graphics.G3D;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Simple3DRenderer {
    private ArrayList<StaticModel> models;

    public Simple3DRenderer() {
        models = new ArrayList<>();
        glEnable(GL_DEPTH_TEST);
        //glEnable(GL_CULL_FACE);
    }

    public void submit(StaticModel model) {
        models.add(model);
    }

    public void flush() {
        for (StaticModel m : models) {
            m.getVAO().bind();
            m.getIBO().bind();
            m.getShader().enable();
            glDrawElements(GL_TRIANGLES, m.getIBO().getCount(), GL_UNSIGNED_INT, NULL);
            m.getIBO().unbind();
            m.getVAO().unbind();
        }
        models.clear();
    }
}
