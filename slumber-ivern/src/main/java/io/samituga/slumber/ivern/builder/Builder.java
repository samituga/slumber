package io.samituga.slumber.ivern.builder;

public interface Builder<T> {
    T build();

    Builder<T> copy(T t);
}
