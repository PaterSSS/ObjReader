package org.main.reader.exeptions;

public class ObjReaderException extends RuntimeException {
    public ObjReaderException(String message, int lineIndex) {
        super("Error while parsing OBJ file on line: " + lineIndex + ". " + message);
    }
}
