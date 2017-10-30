package graphics.G3D;

import graphics.Shader;
import graphics.buffers.Buffer;
import graphics.buffers.IndexBuffer;
import graphics.buffers.VertexArray;

import java.util.Random;

public class StaticModel {
    private Shader shader;
    private VertexArray VAO;
    private IndexBuffer IBO;

    public StaticModel(Mesh m, Shader s) {
        this.shader = s;
        VAO = new VertexArray();
        VAO.addBuffer(new Buffer(m.getVertices(), 3), 0);
        float[] colors = new float[m.getVertices().length * 4];
        Random r = new Random();
        r.setSeed(100);
        for (int i = 0; i < colors.length; i+=4) {
            colors[i] = (float)i / m.getVertices().length;
            colors[i + 1] = (float)i / m.getVertices().length;
            colors[i + 2] = (float)i / m.getVertices().length;
            colors[i + 3] = 1.0f;
        }
        VAO.addBuffer(new Buffer(colors, 4), 1);
        IBO = new IndexBuffer(m.getIndices());
    }

    public Shader getShader() {
        return shader;
    }

    public IndexBuffer getIBO() {
        return IBO;
    }

    public VertexArray getVAO() {
        return VAO;
    }
}
