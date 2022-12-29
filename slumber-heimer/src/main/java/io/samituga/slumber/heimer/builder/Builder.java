package io.samituga.slumber.heimer.builder;

public interface Builder<T> {
    T build();

    Builder<T> copy(T t);
}
