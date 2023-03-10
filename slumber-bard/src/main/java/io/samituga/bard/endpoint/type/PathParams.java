package io.samituga.bard.endpoint.type;

import io.samituga.slumber.ivern.type.MapType;
import java.util.Map;

public class PathParams extends MapType<PathParamName, PathParamValue> {

    private PathParams() {
        super();
    }

    private PathParams(Map<PathParamName, PathParamValue> value) {
        super(value);
    }


    public static PathParams of(Map<PathParamName, PathParamValue> value) {
        return new PathParams(value);
    }

    public static PathParams of(Map.Entry<PathParamName, PathParamValue> value) {
        return new PathParams(Map.ofEntries(value));
    }

    public static PathParams of(PathParamName name, PathParamValue value) {
        return new PathParams(Map.of(name, value));
    }

    public static PathParams empty() {
        return new PathParams();
    }
}
