package io.samituga.bard.endpoint.type;

import static io.samituga.slumber.heimer.validator.AssertionUtility.requiredNotBlank;

import io.samituga.slumber.ivern.type.Type;

public class Path extends Type<String> {

    private Path(String path) {
        super(requiredNotBlank("path", path));
    }

    public static Path of(String path) {
        return new Path(path);
    }
}
