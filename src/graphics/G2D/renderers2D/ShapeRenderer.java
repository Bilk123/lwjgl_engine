package graphics.G2D.renderers2D;

import graphics.G2D.renderables2D.Renderable2D;
import graphics.G2D.shapes.Ellipse;
import graphics.G2D.shapes.Line;
import graphics.G2D.shapes.Point;
import graphics.G2D.shapes.Rectangle;
import graphics.buffers.IndexBuffer;
import math.Vec2;
import math.Vec3;
import math.Vec4;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.system.MemoryUtil.NULL;

@SuppressWarnings("ALL")
public class ShapeRenderer extends Renderer2D {
    private static final int SHAPE_RENDERER_MAX_VERTICES = 10000;
    private static final int SHAPE_VERTEX_SIZE = Vec3.BYTES + Vec4.BYTES;
    private static final long SHAPE_RENDERER_BUFFER_SIZE = SHAPE_RENDERER_MAX_VERTICES * SHAPE_VERTEX_SIZE;

    private static final int SHADER_VERTEX_INDEX = 0;
    private static final int SHADER_COLOR_INDEX = 1;

    private int VAO;
    private IndexBuffer IBO;
    private int indexCount = 0;
    private int indexOffset = 0;
    private int VBO;
    private FloatBuffer vertexData;
    private ArrayList<Integer> indicesData;

    public ShapeRenderer() {
        VAO = glGenVertexArrays();
        VBO = glGenBuffers();

        glBindVertexArray(VAO);
        glBindBuffer(GL_ARRAY_BUFFER, VBO);

        glBufferData(GL_ARRAY_BUFFER, SHAPE_RENDERER_BUFFER_SIZE, GL_DYNAMIC_DRAW);
        glEnableVertexAttribArray(SHADER_VERTEX_INDEX);
        glEnableVertexAttribArray(SHADER_COLOR_INDEX);

        glVertexAttribPointer(SHADER_VERTEX_INDEX, 3, GL_FLOAT, false, SHAPE_VERTEX_SIZE, 0);
        glVertexAttribPointer(SHADER_COLOR_INDEX, 4, GL_FLOAT, false, SHAPE_VERTEX_SIZE, Vec3.BYTES);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
        indicesData = new ArrayList<>();
    }

    @Override
    public void begin() {
        glBindBuffer(GL_ARRAY_BUFFER, VBO);
        vertexData = glMapBuffer(GL_ARRAY_BUFFER, GL_WRITE_ONLY).asFloatBuffer();
    }

    @Override
    public void submit(Renderable2D renderable) {
        if (renderable instanceof Rectangle) {
            Rectangle r = (Rectangle) renderable;
            addRectangle(r);
        } else if (renderable instanceof Ellipse) {
            Ellipse e = (Ellipse) renderable;
            addEllipse(e);
        } else if (renderable instanceof Line) {
            Line l = (Line) renderable;
            addLine(l);
        } else if (renderable instanceof Point) {
            Point p = (Point) renderable;
            addPoint(p);
        }
    }

    private void addRectangle(Rectangle r) {
        Vec2[] vertices = Rectangle.vertices;
        for (int i = 0; i < vertices.length; i++) {
            Vec2 vertex = vertices[i];
            addVertex(r.getTransform().getMatrix().mul(new Vec3(vertex, 0)), r.getColor());
        }
        for (int i : Rectangle.indices) {
            indicesData.add(i + indexOffset);
        }
        indexOffset += 4;
        indexCount += Rectangle.indices.length;
    }

    private void addPoint(Point p) {
        addVertex(new Vec3(p.getPoint(), 0), p.getColor());
        addVertex(new Vec3(p.getPoint().add(0.01f, 0.01f), 0), p.getColor());
        indicesData.add(indexOffset);
        indicesData.add(indexOffset);
        indicesData.add(indexOffset + 1);
        indexOffset += 2;
        indexCount += 3;
    }

    private void addEllipse(Ellipse e) {
        Vec2[] vertices = Ellipse.VERTICES;
        for (int i = 0; i < vertices.length; i++) {
            Vec2 vertex = vertices[i];
            addVertex(e.getTransform().getMatrix().mul(new Vec3(vertex, 0)), e.getColor());
        }
        for (int i : Ellipse.INDICES) {
            indicesData.add(i + indexOffset);
        }
        indexOffset += Ellipse.RESOLUTION + 1;
        indexCount += Ellipse.INDICES.length;
    }

    private void addLine(Line l) {
        Vec2 p1 = l.getP1();
        Vec2 p2 = l.getP2();

        addVertex(new Vec3(p1, 0), l.getColor());
        addVertex(new Vec3(p2, 0), l.getColor());

        for (int i : Line.INDICES) {
            indicesData.add(i + indexOffset);
        }
        indexOffset += 2;
        indexCount += Line.INDICES.length;
    }

    @Override
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

    @Override
    public void flush() {
        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        glBindVertexArray(VAO);
        IBO.bind();
        glDrawElements(GL_TRIANGLES, indexCount, GL_UNSIGNED_INT, NULL);
        IBO.unbind();
        glBindVertexArray(0);
        indexCount = 0;
        indexOffset = 0;
    }

    private void addVertex(Vec3 vertex, Vec4 color) {
        vertexData.put(vertex.x);
        vertexData.put(vertex.y);
        vertexData.put(vertex.z);
        vertexData.put(color.x);
        vertexData.put(color.y);
        vertexData.put(color.z);
        vertexData.put(color.w);
    }
}
