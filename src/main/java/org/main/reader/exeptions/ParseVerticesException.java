package org.main.reader.exeptions;

public class ParseVerticesException extends ObjReaderException {
    public ParseVerticesException(String typeOfNumber, int lineIndex) {
        super("Error while parsing " + typeOfNumber + " on line ", lineIndex);
    }
}
