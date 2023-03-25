package io.samituga.bard.endpoint.request.type;

import io.samituga.slumber.ivern.type.Type;

public class PathParamValue extends Type<String> {

    private PathParamValue(String value) {
        super(value);
    }

    public static PathParamValue of(String value) {
        return new PathParamValue(value);
    }
}
