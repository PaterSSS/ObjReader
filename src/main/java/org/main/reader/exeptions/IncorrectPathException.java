package org.main.reader.exeptions;

public class IncorrectPathException extends RuntimeException {
    public IncorrectPathException() {
        super("Incorrect path to the file");
    }
}
