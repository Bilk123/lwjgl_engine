package graphics.buffers;

import util.BufferUtil;

import static org.lwjgl.opengl.GL15.*;

public class Buffer {
    private final int B_ID;
    private int componentCount;
    private int count;

    public Buffer(float[] data, int componentCount) {
        this.componentCount = componentCount;
        this.count = data.length;
        B_ID = glGenBuffers();
        bind();
        glBufferData(GL_ARRAY_BUFFER, BufferUtil.createBuffer(data), GL_STATIC_DRAW);
        unbind();
    }

    public void bind() {
        glBindBuffer(GL_ARRAY_BUFFER, B_ID);
    }

    public void unbind() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public int getComponentCount() {
        return componentCount;
    }

    public int getCount() {
        return count;
    }
}
