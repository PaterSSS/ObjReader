package org.main.math;

import java.util.Objects;

public class Vector3f {
    float x, y, z;
    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Vector3f tmp)) {
            return false;
        }
        return Float.compare(this.x, tmp.x) == 0 && Float.compare(this.y, tmp.y) == 0 &&
                Float.compare(this.z, tmp.z) == 0;
    }
    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
