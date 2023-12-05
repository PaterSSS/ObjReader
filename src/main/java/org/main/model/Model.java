package org.main.model;

import org.main.math.Vector;
import org.main.math.Vector2f;
import org.main.math.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private List<Vector> vertices = new ArrayList<>();
    private List<Vector> textureVertices = new ArrayList<>();
    private List<Vector> normals = new ArrayList<>();
    private List<Polygon> polygons = new ArrayList<>();
    private List<Group> groups = new ArrayList<>();

    public List<Vector> getTextureVertices() {
        return textureVertices;
    }
    public List<Vector> getVertices() {
        return vertices;
    }
    public List<Vector> getNormals() {
        return normals;
    }
    public List<Group> getGroups() {return groups;}
    public List<Polygon> getPolygons() {
        return polygons;
    }
    public void addVertex(Vector vertex) {
        vertices.add(vertex);
    }
    public void addTextureVertex(Vector vertex) {
        textureVertices.add(vertex);
    }
    public void addNormal(Vector normal) {
        normals.add(normal);
    }
    public void addPolygon(Polygon polygon) {
        polygons.add(polygon);
    }
    public void addGroup(Group group) {
        groups.add(group);
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
