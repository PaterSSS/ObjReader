package org.main.reader.exeptions;

public class IncorrectIndexException extends ObjReaderException{
    public IncorrectIndexException(String message, int lineIndex) {
        super("Incorrect " + message + " in polygon",lineIndex);
    }
}
