package io.samituga.bard.endpoint.request.type;

import io.samituga.slumber.ivern.type.MapType;
import java.util.Map;
import java.util.stream.Collectors;

public class PathParams extends MapType<PathParamName, PathParamValue> {

    private PathParams() {
        super();
    }

    private PathParams(Map<PathParamName, PathParamValue> value) {
        super(value);
    }


    public static PathParams of(PathParamName name, PathParamValue value) {
        return of(Map.of(name, value));
    }

    public static PathParams of(Map.Entry<PathParamName, PathParamValue> value) {
        return of(Map.ofEntries(value));
    }

    public static PathParams of(Map<PathParamName, PathParamValue> value) {
        return new PathParams(value);
    }

    public static PathParams ofString(String name, String value) {
        return ofString(Map.of(name, value));
    }

    public static PathParams ofString(Map.Entry<String, String> value) {
        return ofString(Map.ofEntries(value));
    }

    public static PathParams ofString(Map<String, String> value) {
        return new PathParams(convert(value));
    }

    public static PathParams empty() {
        return new PathParams();
    }

    private static Map<PathParamName, PathParamValue> convert(Map<String, String> pathParamMap) {
        return pathParamMap.entrySet().stream()
              .map(e -> Map.entry(
                    PathParamName.of(e.getKey()),
                    PathParamValue.of(e.getValue())))
              .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
