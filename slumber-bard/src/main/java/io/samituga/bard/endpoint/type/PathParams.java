package io.samituga.bard.endpoint.type;

import io.samituga.slumber.ivern.type.Type;
import java.util.Collections;
import java.util.Map;

public class PathParams extends Type<Map<String, String>> {

    private PathParams(Map<String, String> value) {
        super(value);
    }

    public static PathParams of(Map<String, String> value) {
        return new PathParams(Map.copyOf(value));
    }

    public static PathParams of(Map.Entry<String, String> value) {
        return new PathParams(Map.ofEntries(value));
    }

    public static PathParams empty() {
        return new PathParams(Collections.emptyMap());
    }
}
