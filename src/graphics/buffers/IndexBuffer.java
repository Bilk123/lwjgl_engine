package graphics.buffers;

import util.BufferUtil;

import static org.lwjgl.opengl.GL15.*;

public class IndexBuffer {
    private int I_ID;
    private int count;

    public IndexBuffer(int[] indices) {
        count = indices.length;
        I_ID = glGenBuffers();
        reSubmit(indices);

    }

    public void reSubmit(int [] indices){
        bind();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, BufferUtil.createBuffer(indices), GL_STATIC_DRAW);
        unbind();
    }

    public void bind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, I_ID);
    }

    public void unbind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public int getCount() {
        return count;
    }
}
