package core;

import graphics.layers.Layer2D;

import java.util.ArrayList;

public class Scene2D {
    private ArrayList<Layer2D> layers;


    public Scene2D() {
        layers = new ArrayList<>();
    }

    public void addLayer(Layer2D layer){
        layers.add(layer);
    }



}
