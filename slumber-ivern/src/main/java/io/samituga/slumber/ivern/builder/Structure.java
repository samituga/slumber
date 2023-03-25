package io.samituga.slumber.ivern.builder;

public interface Structure<T, B extends Builder<T>> {

    B copy();
}
