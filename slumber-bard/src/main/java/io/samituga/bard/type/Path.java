package io.samituga.bard.type;

import io.samituga.slumber.heimer.type.Type;

import static io.samituga.slumber.heimer.validator.AssertionUtility.requiredNotBlank;

public class Path extends Type<String> {

    private Path(String path) {
        super(requiredNotBlank("path", path));
    }

    public static Path of(String path) {
        return new Path(path);
    }
}
