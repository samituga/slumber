package io.samituga.slumber.ivern.type;

import java.util.Objects;

public abstract class Type<T> {

    private final T value;

    public Type(T value) {
        this.value = value;
    }

    public T value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Type<?> type = (Type<?>) o;
        return value.equals(type.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
