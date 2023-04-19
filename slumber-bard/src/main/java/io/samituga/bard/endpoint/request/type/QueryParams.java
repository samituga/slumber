package io.samituga.bard.endpoint.request.type;

import io.samituga.slumber.ivern.type.MapType;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class QueryParams extends MapType<String, Set<String>> {

    private QueryParams() {
        super();
    }

    private QueryParams(Map<String, Set<String>> value) {
        super(value);
    }

    public static QueryParams of(String name, String value) {
        return of(Map.of(name, Set.of(value)));
    }

    public static QueryParams of(String name, Set<String> value) {
        return of(Map.of(name, value));
    }

    public static QueryParams of(Map.Entry<String, Set<String>> value) {
        return of(Map.ofEntries(value));
    }

    public static QueryParams of(Map<String, Set<String>> value) {
        return new QueryParams(value);
    }

//    public static QueryParams of(Map<String, List<String>> value) {
//        return new QueryParams(value);
//    }


    public String getFirst(String paramName) {
        return get(paramName).stream()
              .findFirst()
              .orElseThrow();
    }

    public Optional<String> findFirst(String paramName) {
        return get(paramName).stream()
              .findFirst();
    }

    public static QueryParams empty() {
        return new QueryParams();
    }
}
