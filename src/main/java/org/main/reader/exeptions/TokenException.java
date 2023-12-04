package org.main.reader.exeptions;

public class TokenException extends ObjReaderException {
    public TokenException(int lineIndex) {
        super("Unexpected token", lineIndex);
    }
}
