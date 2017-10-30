package graphics.G2D;

import graphics.RenderUtil;
import graphics.buffers.IndexBuffer;
import math.Vec2;
import math.Vec3;
import math.Vec4;

import java.nio.FloatBuffer;

import static graphics.Shader.SHADER_COLOR_INDEX;
import static graphics.Shader.SHADER_VERTEX_INDEX;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class SpriteBatch extends Renderer2D {
    private static final int BATCH2D_MAX_SPRITES = 60000;
    private static final int BATCH2D_VERTEX_SIZE = Vec3.BYTES + Vec4.BYTES;
    private static final int BATCH2D_SPRITE_SIZE = BATCH2D_VERTEX_SIZE * 4;
    private static final int BATCH2D_BUFFER_SIZE = BATCH2D_SPRITE_SIZE * BATCH2D_MAX_SPRITES;
    private static final int BATCH2D_INDICES_SIZE = BATCH2D_MAX_SPRITES * 6;

    private int VAO;
    private IndexBuffer IBO;
    private int indexCount = 0;
    private int VBO;
    private FloatBuffer vertexData;

    public SpriteBatch() {
        VAO = glGenVertexArrays();
        VBO = glGenBuffers();

        glBindVertexArray(VAO);
        glBindBuffer(GL_ARRAY_BUFFER, VBO);
        glBufferData(GL_ARRAY_BUFFER, BATCH2D_BUFFER_SIZE, GL_DYNAMIC_DRAW);

        glEnableVertexAttribArray(SHADER_VERTEX_INDEX);
        glEnableVertexAttribArray(SHADER_COLOR_INDEX);

        glVertexAttribPointer(SHADER_VERTEX_INDEX, Vec3.COMPONENTS, GL_FLOAT, false, BATCH2D_VERTEX_SIZE, 0);
        glVertexAttribPointer(SHADER_COLOR_INDEX, Vec4.COMPONENTS, GL_FLOAT, false, BATCH2D_VERTEX_SIZE, Vec3.BYTES);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

        int[] indices = new int[BATCH2D_INDICES_SIZE];
        for (int i = 0, offset = 0; i < BATCH2D_INDICES_SIZE; i += 6, offset += 4) {
            indices[i] = offset;
            indices[i + 1] = offset + 1;
            indices[i + 2] = offset + 2;
            indices[i + 3] = offset + 2;
            indices[i + 4] = offset + 3;
            indices[i + 5] = offset;
        }

        IBO = new IndexBuffer(indices);
    }
    @Override
    public void begin() {
        glBindBuffer(GL_ARRAY_BUFFER, VBO);
        vertexData = glMapBuffer(GL_ARRAY_BUFFER, GL_WRITE_ONLY).asFloatBuffer();
    }

    @Override
    public void submit(Renderable2D s) {
        Vec2 size = s.getSize();
        Vec3 pos = s.getPosition();
        Vec4 color = s.getColor();


        addVertex(pos, color);
        addVertex(pos.add(0, size.y, 0), color);
        addVertex(pos.add(size.x,size.y,0), color);
        addVertex(pos.add(size.x,0,0), color);

        indexCount += 6;
    }

    @Override
    public void end() {
        vertexData.flip();
        glUnmapBuffer(GL_ARRAY_BUFFER);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
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

    @Override
    public void flush() {
        RenderUtil.batchFlush(IBO,VAO,indexCount);
        indexCount=0;
    }
 }
