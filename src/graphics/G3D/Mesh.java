package graphics.G3D;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Mesh {
    private int[] indices;
    private float[] vertices;

    public Mesh(String modelPath) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Mesh.class.getClassLoader().getResourceAsStream(modelPath)))) {
            String line;
            ArrayList<Integer> indicesTemp = new ArrayList<>();
            ArrayList<Float> verticesTemp = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(" ");
                if (tokens[0].equals("v")) {
                    verticesTemp.add(Float.valueOf(tokens[1]));
                    verticesTemp.add(Float.valueOf(tokens[2]));
                    verticesTemp.add(Float.valueOf(tokens[3]));
                }
                else if (tokens[0].equals("f")) {
                    indicesTemp.add(Integer.parseInt(tokens[1]) - 1);
                    indicesTemp.add(Integer.parseInt(tokens[2]) - 1);
                    indicesTemp.add(Integer.parseInt(tokens[3]) - 1);
                }
            }

            vertices = new float[verticesTemp.size()];
            for(int i = 0;i<verticesTemp.size();i++){
                vertices[i] = verticesTemp.get(i);
            }
            indices = new int[indicesTemp.size()];
            for(int i = 0;i<indicesTemp.size();i++){
                indices[i]= indicesTemp.get(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public float[] getVertices() {
        return vertices;
    }

    public int[] getIndices() {
        return indices;
    }
}
