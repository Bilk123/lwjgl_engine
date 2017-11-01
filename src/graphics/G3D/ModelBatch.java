package graphics.G3D;

import graphics.RenderUtil;
import graphics.Shader;
import graphics.buffers.IndexBuffer;
import math.Vec3;
import math.Vec4;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import static graphics.G2D.SpriteBatch.SHADER_COLOR_INDEX;
import static graphics.G2D.SpriteBatch.SHADER_VERTEX_INDEX;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class ModelBatch {
    public static final int BATCH3D_MAX_VERTICES = 100000;
    public static final int BATCH3D_VERTEX_SIZE = Vec3.BYTES + Vec4.BYTES;
    public static final int BATCH3D_BUFFER_SIZE = BATCH3D_VERTEX_SIZE * BATCH3D_MAX_VERTICES;

    private Shader shader;
    private ArrayList<Integer> indicesData;

    private int VAO;
    private IndexBuffer IBO;
    private int indexCount = 0;
    private int VBO;
    private FloatBuffer vertexData;

    public ModelBatch(Shader s) {
        shader = s;
        VAO = glGenVertexArrays();
        VBO = glGenBuffers();
        glBindVertexArray(VAO);
        glBindBuffer(GL_ARRAY_BUFFER, VBO);
        glBufferData(GL_ARRAY_BUFFER, BATCH3D_BUFFER_SIZE, GL_DYNAMIC_DRAW);

        glEnableVertexAttribArray(SHADER_VERTEX_INDEX);
        glEnableVertexAttribArray(SHADER_COLOR_INDEX);

        glVertexAttribPointer(SHADER_VERTEX_INDEX, Vec3.COMPONENTS, GL_FLOAT, false, BATCH3D_VERTEX_SIZE, 0);
        glVertexAttribPointer(SHADER_COLOR_INDEX, Vec4.COMPONENTS, GL_FLOAT, false, BATCH3D_VERTEX_SIZE, Vec3.BYTES);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
        indicesData = new ArrayList<>();
    }

    public void begin() {
        glBindBuffer(GL_ARRAY_BUFFER, VBO);
        vertexData = glMapBuffer(GL_ARRAY_BUFFER, GL_WRITE_ONLY).asFloatBuffer();
    }

    public void submit(Model m) {
        float[] floats = m.getMesh().getVertices();
        for (int i = 0; i < floats.length; i += 3) {
            addVertex(new Vec3(floats[i], floats[i + 1], floats[i + 2]), new Vec4(1.0f, 1.0f, 1.0f, 1.0f));
        }
        for (int i : m.getMesh().getIndices())
            indicesData.add(i);
        indexCount += m.getMesh().getIndices().length;
    }

    public void end() {
        vertexData.flip();
        int[] indices = new int[indicesData.size()];
        for (int i = 0; i < indicesData.size(); i++) {
            indices[i] = indicesData.get(i);
        }
        IBO = new IndexBuffer(indices);
        glUnmapBuffer(GL_ARRAY_BUFFER);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        indicesData.clear();
    }

    private void addVertex(Vec3 pos, Vec4 color) {
        vertexData.put(pos.x);
        vertexData.put(pos.y);
        vertexData.put(pos.z);
        vertexData.put(color.x);
        vertexData.put(color.y);
        vertexData.put(color.z);
        vertexData.put(color.w);
    }

    public void flush() {
        RenderUtil.batchFlush(IBO, VAO, indexCount);
        indexCount = 0;
    }


}
