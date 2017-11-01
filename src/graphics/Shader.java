package graphics;

import com.sun.istack.internal.NotNull;
import math.Mat4;
import math.Vec2;
import math.Vec3;
import math.Vec4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

public class Shader {
    private int shaderID;
    private String vertexShaderPath;
    private String fragmentShaderPath;


    public Shader(String vertexShaderPath, String fragmentShaderPath) {
        this.fragmentShaderPath = fragmentShaderPath;
        this.vertexShaderPath = vertexShaderPath;
        shaderID = createShader();
    }

    public void delete() {
        glDeleteProgram(shaderID);
    }

    public final void enable() {
        glUseProgram(shaderID);
    }

    public final void disable() {
        glUseProgram(0);
    }

    private int createShader() {
        int program = glCreateProgram();
        int vs = compileShader(GL_VERTEX_SHADER, vertexShaderPath);
        int fs = compileShader(GL_FRAGMENT_SHADER, fragmentShaderPath);
        glAttachShader(program, vs);
        glAttachShader(program, fs);

        glLinkProgram(program);
        if (glGetProgrami(program, GL_LINK_STATUS) != 1) {
            System.out.println("Failed to link shader program");
            System.out.println(glGetProgramInfoLog(program));
        }
        glValidateProgram(program);

        if (glGetProgrami(program, GL_VALIDATE_STATUS) != 1) {
            System.out.println("Failed to validate shader program");
            System.out.println(glGetProgramInfoLog(program));
        }
        glDeleteShader(vs);
        glDeleteShader(fs);
        return program;
    }

    private static int compileShader(int type, @NotNull String fileName) {
        int id = glCreateShader(type);
        glShaderSource(id, getSourceCode(fileName));
        glCompileShader(id);

        int i = glGetShaderi(id, GL_COMPILE_STATUS);
        if (i == GL_FALSE) {
            System.out.println("Error compiling " + fileName);
            System.out.println(glGetShaderInfoLog(id));
        }
        return id;
    }

    private static String getSourceCode(String fileName) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Shader.class.getClassLoader().getResourceAsStream(fileName)))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private int getUniformLocation(String name) {
        return glGetUniformLocation(shaderID, name);
    }

    public void setUniformMat4(String name, Mat4 m) {
        glUniformMatrix4fv(getUniformLocation(name), true, m.mat);
    }

    public void setUniformVec2(String name, Vec2 v) {
        glUniform2f(getUniformLocation(name), v.x, v.y);
    }

    public void setUniformVec3(String name, Vec3 v) {
        glUniform3f(getUniformLocation(name), v.x, v.y, v.z);
    }

    public void setUniformVec4(String name, Vec4 v) {
        glUniform4f(getUniformLocation(name), v.x, v.y, v.z, v.w);
    }

    public void setUniformFloat(String name, float val) {
        glUniform1f(getUniformLocation(name), val);
    }

    public void setUniformFloatV(String name, float[] data) {
        glUniform1fv(getUniformLocation(name), data);
    }

    public void setUniformInt(String name, int val) {
        glUniform1i(getUniformLocation(name), val);
    }

    public void setUniformIntV(String name, int[] data) {
        glUniform1iv(getUniformLocation(name), data);
    }

}
