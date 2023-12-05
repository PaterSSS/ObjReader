package org.main.reader.exeptions;

public class IncorrectCountOfArgumentsException extends ObjReaderException {
    public IncorrectCountOfArgumentsException(TypeOfError type, int lineIndex) {
        super("Too " + type.getTextValue() + " arguments", lineIndex);
    }
}
