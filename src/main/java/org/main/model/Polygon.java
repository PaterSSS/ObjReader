package org.main.model;

import java.util.ArrayList;
import java.util.List;

public class Polygon {
    private List<Integer> vertexIndices;
    private List<Integer> textureVertexIndices;
    private List<Integer> normalIndices;
    private int lineIndex;

    public Polygon() {
        vertexIndices = new ArrayList<>();
        textureVertexIndices = new ArrayList<>();
        normalIndices = new ArrayList<>();
    }

    public void setVertexIndices(List<Integer> vertexIndices) {
        this.vertexIndices = vertexIndices;
    }

    public void setTextureVertexIndices(List<Integer> textureVertexIndices) {
        this.textureVertexIndices = textureVertexIndices;
    }

    public void setNormalIndices(List<Integer> normalIndices) {
        this.normalIndices = normalIndices;
    }

    public List<Integer> getVertexIndices() {
        return vertexIndices;
    }

    public List<Integer> getTextureVertexIndices() {
        return textureVertexIndices;
    }

    public List<Integer> getNormalIndices() {
        return normalIndices;
    }

    public void setLineIndex(int lineIndex) {
        this.lineIndex = lineIndex;
    }
    public int getLineIndex() {
        return lineIndex;
    }
    public boolean hasTextures() {
        return !textureVertexIndices.isEmpty();
    }
}
