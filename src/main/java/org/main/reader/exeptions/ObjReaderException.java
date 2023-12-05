package org.main.reader.exeptions;

public class ObjReaderException extends RuntimeException {
    private int lineIndex;
    public ObjReaderException(String message, int lineIndex) {
        super("Error while parsing OBJ file. Problem: " + message + ". On line " + lineIndex);
        this.lineIndex = lineIndex;
    }
    public int getLineIndex() {
        return lineIndex;
    }
    //может сделать чтобы всё наследовалось от одного эксепсиона, и там выстроить архитектуру.
}
