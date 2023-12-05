package org.main.math.exceptions;

public class VectorCountArgumentsException extends RuntimeException {
    public VectorCountArgumentsException() {
        super("Incorrect number of vertex in vector 1 or less");
    }
}
