package org.main.math;

import org.main.math.exceptions.VectorCountArgumentsException;

import java.util.Objects;

public class Vector {
    private float[] values;

    public Vector(float... vertices) {
        if (vertices.length < 2) {
            throw new VectorCountArgumentsException();
        }
        this.values = vertices;
    }
    public float[] getValues() {
        return values;
    }
    public void setValues(float[] vertices) {
        this.values = vertices;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Vector tmp)) {
            return false;
        }
        if (this.getLengthOfVector() != tmp.getLengthOfVector()) {
            return false;
        }
        for (int i = 0; i < this.getLengthOfVector(); i++) {
            if (values[i] != tmp.values[i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(values);
    }

    public int getLengthOfVector() {
        return values.length;
    }
}
