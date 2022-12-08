package io.samituga.slumber.heimer.builder;

public interface Builder<T> {
    T build();

    T copy(T t);
}
