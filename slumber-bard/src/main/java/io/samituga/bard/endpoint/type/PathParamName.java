package io.samituga.bard.endpoint.type;

import io.samituga.slumber.heimer.type.Type;

public class PathParamName extends Type<String> {
    private PathParamName(String value) {
        super(value);
    }

    public static PathParamName of(String value) {
        return new PathParamName(value);
    }
}
