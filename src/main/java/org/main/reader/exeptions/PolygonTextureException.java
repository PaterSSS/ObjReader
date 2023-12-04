package org.main.reader.exeptions;

import org.main.model.Polygon;

public class PolygonTextureException extends ObjReaderException {
    public PolygonTextureException(int lineIndex) {
        super("Some polygons have textures and some do not", lineIndex);
    }
}
