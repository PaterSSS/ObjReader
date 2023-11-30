package org.main.model;

import java.util.ArrayList;
import java.util.List;

public class Polygon {
    private List<Integer> vertexIndices;
    private List<Integer> textureVertexIndices;
    private List<Integer> normalIndices;

    public Polygon(List<Integer> vertexIndices, List<Integer> textureVertexIndices, List<Integer> normalIndices) {
        this.vertexIndices = vertexIndices;
        this.textureVertexIndices = textureVertexIndices;
        this.normalIndices = normalIndices;
    }

    public Polygon(List<Integer> vertexIndices) {
        this.vertexIndices = vertexIndices;
        textureVertexIndices = new ArrayList<>();
        normalIndices = new ArrayList<>();
    }
    public Polygon() {
        vertexIndices = new ArrayList<>();
        textureVertexIndices = new ArrayList<>();
        normalIndices = new ArrayList<>();
    }
}
