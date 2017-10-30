package graphics.buffers;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class VertexArray {
    private final int VA_ID;


    public VertexArray() {
        VA_ID = glGenVertexArrays();
    }

    public void addBuffer(Buffer b, int index) {
        bind();
        b.bind();
        glEnableVertexAttribArray(index);
        glVertexAttribPointer(index, b.getComponentCount(), GL_FLOAT, false, 0, 0);
        b.unbind();
        unbind();
    }

    public void bind() {
        glBindVertexArray(VA_ID);

    }

    public void unbind() {
        glBindVertexArray(0);
    }
}
