package graphics.G2D.renderers2D;

import graphics.G2D.renderables2D.Renderable2D;
import util.RenderUtil;
import graphics.buffers.IndexBuffer;
import math.Mat4;
import math.Vec2;
import math.Vec3;
import math.Vec4;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class SpriteBatch extends Renderer2D {
    private static final int BATCH2D_MAX_SPRITES = 60000;
    private static final int BATCH2D_VERTEX_SIZE = Vec3.BYTES + Vec4.BYTES + Vec2.BYTES + Integer.BYTES;
    private static final int BATCH2D_SPRITE_SIZE = BATCH2D_VERTEX_SIZE * 4;
    private static final int BATCH2D_BUFFER_SIZE = BATCH2D_SPRITE_SIZE * BATCH2D_MAX_SPRITES;
    private static final int BATCH2D_INDICES_SIZE = BATCH2D_MAX_SPRITES * 6;

    public static final int SHADER_VERTEX_INDEX = 0;
    private static final int SHADER_UV_INDEX = 1;
    private static final int SHADER_TID_INDEX = 2;
    public static final int SHADER_COLOR_INDEX = 3;

    private int VAO;
    private IndexBuffer IBO;
    private int indexCount = 0;
    private int VBO;
    private FloatBuffer vertexData;
    private ArrayList<Integer> textureSlots;

    public SpriteBatch() {
        textureSlots = new ArrayList<>();
        VAO = glGenVertexArrays();
        VBO = glGenBuffers();

        glBindVertexArray(VAO);
        glBindBuffer(GL_ARRAY_BUFFER, VBO);
        glBufferData(GL_ARRAY_BUFFER, BATCH2D_BUFFER_SIZE, GL_DYNAMIC_DRAW);

        glEnableVertexAttribArray(SHADER_VERTEX_INDEX);
        glEnableVertexAttribArray(SHADER_UV_INDEX);
        glEnableVertexAttribArray(SHADER_TID_INDEX);
        glEnableVertexAttribArray(SHADER_COLOR_INDEX);

        glVertexAttribPointer(SHADER_VERTEX_INDEX, Vec3.COMPONENTS, GL_FLOAT, false, BATCH2D_VERTEX_SIZE, 0);
        glVertexAttribPointer(SHADER_UV_INDEX, Vec2.COMPONENTS, GL_FLOAT, false, BATCH2D_VERTEX_SIZE, Vec3.BYTES);
        glVertexAttribPointer(SHADER_TID_INDEX, 1, GL_FLOAT, false, BATCH2D_VERTEX_SIZE, Vec3.BYTES + Vec2.BYTES);
        glVertexAttribPointer(SHADER_COLOR_INDEX, Vec4.COMPONENTS, GL_FLOAT, false, BATCH2D_VERTEX_SIZE, Vec3.BYTES + Vec2.BYTES + Integer.BYTES);

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
        Mat4 transformStackMat = transformStackBack;
        int tID = s.getTextureID();
        Vec4 color;
        color = s.getColor();


        float ts = 0;
        if (tID > 0) {
            boolean found = false;
            for (int i = 0; i < textureSlots.size(); i++) {
                if (textureSlots.get(i) == tID){
                    ts = i+1;
                    found =true;
                    break;
                }
            }

            if (!found) {
                if (textureSlots.size() >= 32) {
                    end();
                    flush();
                    begin();
                }
                textureSlots.add(tID);
                ts = textureSlots.size();
            }

        }
        Mat4 matrix = s.getTransform().getMatrix();
        addVertex(transformStackMat.mul(matrix.mul(new Vec3(0, 0, 0))), s.getUvs().get(0), ts, color);
        addVertex(transformStackMat.mul(matrix.mul(new Vec3(0, 1, 0))), s.getUvs().get(1), ts, color);
        addVertex(transformStackMat.mul(matrix.mul(new Vec3(1, 1, 0))), s.getUvs().get(2), ts, color);
        addVertex(transformStackMat.mul(matrix.mul(new Vec3(1, 0, 0))), s.getUvs().get(3), ts, color);

        indexCount += 6;
    }

    @Override
    public void end() {
        vertexData.flip();
        glUnmapBuffer(GL_ARRAY_BUFFER);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    @Override
    public void flush() {
        glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        for (int i = 0; i < textureSlots.size(); i++) {
            glActiveTexture(GL_TEXTURE0 + i);
            glBindTexture(GL_TEXTURE_2D, textureSlots.get(i));
        }

        RenderUtil.batchFlush(IBO, VAO, indexCount);
        indexCount = 0;
    }

    private void addVertex(Vec3 pos, Vec2 uv, float tID, Vec4 color) {
        vertexData.put(pos.x);
        vertexData.put(pos.y);
        vertexData.put(pos.z);
        vertexData.put(uv.x);
        vertexData.put(uv.y);
        vertexData.put(tID);
        vertexData.put(color.x);
        vertexData.put(color.y);
        vertexData.put(color.z);
        vertexData.put(color.w);
    }

}
