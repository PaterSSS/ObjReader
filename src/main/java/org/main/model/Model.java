package org.main.model;

import org.main.math.Vector2f;
import org.main.math.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private ArrayList<Vector3f> vertices = new ArrayList<>();
    private ArrayList<Vector2f> textureVertices = new ArrayList<>();
    private ArrayList<Vector3f> normals = new ArrayList<>();
    private ArrayList<Polygon> polygons = new ArrayList<>();

    public List<Vector2f> getTextureVertices() {
        return textureVertices;
    }
    public List<Vector3f> getVertices() {
        return vertices;
    }
    public List<Vector3f> getNormals() {
        return normals;
    }
    public List<Polygon> getPolygons() {
        return polygons;
    }
    public void addVertex(Vector3f vertex) {
        vertices.add(vertex);
    }
    public void addTextureVertex(Vector2f vertex) {
        textureVertices.add(vertex);
    }
    public void addNormal(Vector3f normal) {
        normals.add(normal);
    }
    public void addPolygon(Polygon polygon) {
        polygons.add(polygon);
    }

    public int getVertexSize() {
        return vertices.size();
    }
    public int getTextureVertexSize() {
        return textureVertices.size();
    }
    public int getNormalSize() {
        return normals.size();
    }
}
