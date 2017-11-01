package graphics;

import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

public class Texture {
    private int id;

    public Texture(String texturePath, boolean interpolate) {
        try {
            BufferedImage img = ImageIO.read(Texture.class.getClassLoader().getResourceAsStream(texturePath));
            int width = img.getWidth();
            int height = img.getHeight();
            int[] pixels_raw;
            pixels_raw = img.getRGB(0, 0, width, height, null, 0, width);

            ByteBuffer pixels = BufferUtils.createByteBuffer(width * height * 4);
            for (int j = 0; j < height; j++) {
                for (int i = 0; i < width; i++) {
                    int pixel = pixels_raw[i + j * width];
                    int r = ((pixel >> 16) & 0xff);
                    int g = ((pixel >> 8) & 0xff);
                    int b = (pixel & 0xff);
                    int a = ((pixel >> 24) & 0xff);

                    pixels.put((byte) r);
                    pixels.put((byte) g);
                    pixels.put((byte) b);
                    pixels.put((byte) a);
                }
            }
            pixels.flip();

            id = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, id);
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, interpolate ? GL_LINEAR : GL_NEAREST);
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, interpolate ? GL_LINEAR : GL_NEAREST);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getID() {
        return id;
    }
}
