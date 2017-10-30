package graphics;

import graphics.buffers.IndexBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.system.MemoryUtil.NULL;

public class RenderUtil {
    private RenderUtil(){}

    public static void batchFlush(IndexBuffer IBO, int VAO , int indexCount){
        glBindVertexArray(VAO);
        IBO.bind();
        glDrawElements(GL_TRIANGLES, indexCount, GL_UNSIGNED_INT, NULL);
        IBO.unbind();
        glBindVertexArray(0);
    }

}
