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

    // TODO: 2023-02-26 Get path without parameters or query
    // TODO: 2023-02-26 Get only query
    // TODO: 2023-02-26 Get only parameters
    // TODO: 2023-03-10 Replace variables with given value (returns copy)
}
