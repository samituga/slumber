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
        return value.equals(type.value); // TODO: 15/04/2023 Doesn't work if T is array
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
