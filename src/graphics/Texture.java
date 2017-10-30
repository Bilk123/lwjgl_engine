package graphics;

import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

public class Texture {
    private int id;

    public Texture(String texturePath) {
        int width, height;
        try {
            BufferedImage img = ImageIO.read(Texture.class.getClassLoader().getResourceAsStream(texturePath));
            width = img.getWidth();
            height = img.getHeight();
            int[] pixels_raw;
            pixels_raw = img.getRGB(0, 0, width, height, null, 0, width);

            ByteBuffer pixels = BufferUtils.createByteBuffer(width * height * 4);

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    int pixel = pixels_raw[i + j * width];
                    //r
                    pixels.put((byte) ((pixel >> 16) & 0xff));
                    //g
                    pixels.put((byte) ((pixel >> 8) & 0xff));
                    //b
                    pixels.put((byte) (pixel & 0xff));
                    //a
                    pixels.put((byte) ((pixel >> 24) & 0xff));
                }
            }
            pixels.flip();

            id = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, id);
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }
}
