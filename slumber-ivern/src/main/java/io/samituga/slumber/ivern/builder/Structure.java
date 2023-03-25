package io.samituga.slumber.ivern.builder;

public interface Structure<T extends Structure<T, B>, B extends Builder<T>> {

    B copy();
}
