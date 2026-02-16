package com.graphX.graphX.helper;

import java.util.Objects;

public class Triple<A, B, C> {
    private final A first;
    private final B second;
    private final C third;

    public Triple(A first, B second, C third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Triple<?, ?, ?> triple)) return false;

        return first == triple.first && second == triple.second && third == triple.third;
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second, third);
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ", " + third + ")";
    }
}
