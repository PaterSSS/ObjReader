package org.main.reader.exeptions;

public class DifferentTypeOfPolygonException extends ObjReaderException {
    public DifferentTypeOfPolygonException(int lineIndex) {
        super("Different data in polygon", lineIndex);
    }
}
