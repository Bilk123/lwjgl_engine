package graphics.font;

import graphics.G2D.Renderable2D;
import graphics.Texture;
import graphics.Transform;
import math.Vec2;
import math.Vec3;
import math.Vec4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Font {

    private HashMap<java.lang.Character, Font.Character> charMap;
    private ArrayList<Kerning> kernings;

    private float sizeDefault, size;
    private float width, height;
    private Texture atlas;

    public Font(String fontFilePath, String fontAtlasPath, int size) {
        this.size = size;
        atlas = new Texture(fontAtlasPath, false);
        charMap = new HashMap<>();
        kernings = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Font.class.getClassLoader().getResourceAsStream(fontFilePath)))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(" ");
                if (tokens[0].contains("info")) {
                    getInfo(tokens);
                }
                if (tokens[0].contains("common")) {
                    getCommon(tokens);
                }
                if (tokens[0].equals("char")) {
                    loadChars(tokens);
                }
                if (tokens[0].equals("kerning")) {
                    loadKernings(tokens);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void loadKernings(String[] tokens) {
        int first = 0, second = 0;
        float amount = 0;
        for (String token : tokens) {
            String[] contents = token.split("=");
            if (token.startsWith("first")) {
                first = (Integer.parseInt(contents[1]));
            }
            if (token.startsWith("second")) {
                second = (Integer.parseInt(contents[1]));
            }
            if (token.startsWith("amount")) {
                amount = Float.parseFloat(contents[1]) * size / sizeDefault;
            }
        }
        kernings.add(new Kerning((char) first, (char) second, amount));
    }

    private void loadChars(String[] tokens) {
        int id, x, y, width, height, xOffset, yOffset, xAdvance;
        id = x = y = width = height = xOffset = yOffset = xAdvance = 0;
        for (String token : tokens) {
            String[] contents = token.split("=");
            if (token.startsWith("id")) {
                id = Integer.parseInt(contents[1]);
            }
            if (token.startsWith("x") && !token.contains("offset") && !token.contains("advance")) {
                x = Integer.parseInt(contents[1]);
            }
            if (token.startsWith("y") && !token.contains("offset") && !token.contains("advance")) {
                y = Integer.parseInt(contents[1]);
            }
            if (token.startsWith("width")) {
                width = Integer.parseInt(contents[1]);
            }
            if (token.startsWith("height")) {
                height = Integer.parseInt(contents[1]);
            }
            if (token.startsWith("xoffset")) {
                xOffset = Integer.parseInt(contents[1]);
            }
            if (token.startsWith("yoffset")) {
                yOffset = Integer.parseInt(contents[1]);
            }
            if (token.startsWith("xadvance")) {
                xAdvance = Integer.parseInt(contents[1]);
            }
        }
        charMap.put((char) id, new Character(
                new Vec2(x / this.width, y / this.height),
                new Vec2(width / this.width, height / this.height),
                new Vec3(xOffset, yOffset, 0), xAdvance, this));
    }

    private void getCommon(String[] tokens) {
        for (String token : tokens) {
            String[] contents = token.split("=");
            if (token.contains("scaleW")) {
                width = Integer.parseInt(contents[1]);
            }

            if (token.contains("scaleH")) {
                height = Integer.parseInt(contents[1]);
            }
        }
    }

    private void getInfo(String[] tokens) {
        for (String token : tokens) {
            String[] contents = token.split("=");
            if (token.contains("size")) {
                sizeDefault = Integer.parseInt(contents[1]);
            }
        }
    }

    public float getStringWidth(String word) {
        float width = 0;
        for (char c : word.toCharArray()) {
            width += charMap.get(c).width;
        }
        return width;
    }

    public float getSize() {
        return size;
    }

    HashMap<java.lang.Character, Font.Character> getCharMap() {
        return charMap;
    }

    public ArrayList<Kerning> getKernings() {
        return kernings;
    }

    static class Character extends Renderable2D {
        private float xAdvance;
        private float width;

        private Character(Vec2 atlasCoords, Vec2 characterSize, Vec3 offset, float xAdvance, Font f) {
            super(new Vec3(0, 0, 0), characterSize.mul(f.width, f.height).mul(f.size / f.sizeDefault), new Vec4(1, 1, 1, 1));
            this.width = characterSize.x * f.width * f.size / f.sizeDefault;
            this.xAdvance = xAdvance * f.size / f.sizeDefault;
            this.texture = f.atlas;
            transform = transform.mul(Transform.initTranslation(offset.mul(f.size / f.sizeDefault)));
            uvs.add(atlasCoords);
            uvs.add(atlasCoords.add(0, characterSize.y));
            uvs.add(atlasCoords.add(characterSize.x, characterSize.y));
            uvs.add(atlasCoords.add(characterSize.x, 0));
        }

        float getXAdvance() {
            return xAdvance;
        }
    }

    static class Kerning {
        private char first, second;
        private float amount;

        public Kerning(char first, char second, float amount) {
            this.first = first;
            this.second = second;
            this.amount = amount;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Kerning) {
                Kerning k = (Kerning) obj;
                return first == k.first && second == k.second && amount == k.amount;
            } else
                return false;
        }

        @Override
        public String toString() {
            return "first: " + first + " second: " + second;
        }

        public char getFirst() {
            return first;
        }

        public char getSecond() {
            return second;
        }

        public float getAmount() {
            return amount;
        }
    }
}
