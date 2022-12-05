package io.samituga.slumber.builder;

public interface Builder<T> {
    T build();

    T copy(T t);
}
