package org.main.math;

import java.util.regex.Pattern;

public class Vector2f {
    float x, y;
    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Vector2f tmp)) {
            return false;
        }

        return Float.compare(this.x, tmp.x) == 0 && Float.compare(this.y, tmp.y) == 0;
    }
}
