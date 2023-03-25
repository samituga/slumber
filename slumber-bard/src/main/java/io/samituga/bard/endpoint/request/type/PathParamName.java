package io.samituga.bard.endpoint.request.type;

import io.samituga.slumber.ivern.type.Type;

public class PathParamName extends Type<String> {

    private PathParamName(String value) {
        super(value);
    }

    public static PathParamName of(String value) {
        return new PathParamName(value);
    }
}
