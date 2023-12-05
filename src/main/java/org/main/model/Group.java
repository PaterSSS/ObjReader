package org.main.model;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private String nameOfGroup;
    List<Integer> indicesOfPolygons;
    public Group(String name) {
        nameOfGroup = name;
        indicesOfPolygons = new ArrayList<>();
    }
    public String getNameOfGroup() {
        return nameOfGroup;
    }
    public void addNewIndex(int polygonIndex) {
        indicesOfPolygons.add(polygonIndex);
    }
    public List<Integer> getIndicesOfPolygons() {
        return indicesOfPolygons;
    }
}
