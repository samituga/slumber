package io.samituga.bard.endpoint.request.type;

import io.samituga.slumber.ivern.type.MapType;
import java.util.Map;
import java.util.stream.Collectors;

public class PathParams extends MapType<String, String> {

    private PathParams() {
        super();
    }

    private PathParams(Map<String, String> value) {
        super(value);
    }

    public static PathParams of(String name, String value) {
        return of(Map.of(name, value));
    }

    public static PathParams of(Map.Entry<String, String> value) {
        return of(Map.ofEntries(value));
    }

    public static PathParams of(Map<String, String> value) {
        return new PathParams(value);
    }

    public static PathParams empty() {
        return new PathParams();
    }
}
