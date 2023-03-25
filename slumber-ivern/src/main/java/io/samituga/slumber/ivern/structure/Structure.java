package io.samituga.slumber.ivern.structure;

import io.samituga.slumber.ivern.builder.Builder;

public interface Structure<T extends Structure<T, B>, B extends Builder<T>> {

    B copy();
}
