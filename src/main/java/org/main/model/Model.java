package org.main.model;

import org.main.math.Vector2f;
import org.main.math.Vector3f;

import java.util.ArrayList;

public class Model {
    private ArrayList<Vector3f> vertices = new ArrayList<>();
    private ArrayList<Vector2f> textureVertices = new ArrayList<>();
    private ArrayList<Vector3f> normals = new ArrayList<>();
    private ArrayList<Polygon> polygons = new ArrayList<>();
}
