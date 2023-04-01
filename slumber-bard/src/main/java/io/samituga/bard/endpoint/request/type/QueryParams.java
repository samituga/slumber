package io.samituga.bard.endpoint.request.type;

import java.util.List;
import io.samituga.slumber.ivern.type.MapType;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class QueryParams extends MapType<QueryParamName, Set<QueryParamValue>> {

    private QueryParams() {
        super();
    }

    private QueryParams(Map<QueryParamName, Set<QueryParamValue>> value) {
        super(value);
    }


    public static QueryParams of(Map<QueryParamName, Set<QueryParamValue>> value) {
        return new QueryParams(value);
    }

    public static QueryParams of(Map.Entry<QueryParamName, Set<QueryParamValue>> value) {
        return new QueryParams(Map.ofEntries(value));
    }

    public static QueryParams of(QueryParamName name, Set<QueryParamValue> value) {
        return new QueryParams(Map.of(name, value));
    }

    public static QueryParams of(QueryParamName name, QueryParamValue value) {
        return new QueryParams(Map.of(name, Set.of(value)));
    }

    public static QueryParams ofString(String name, String value) {
        return ofString(Map.of(name, List.of(value)));
    }

    public static QueryParams ofString(String name, List<String> value) {
        return ofString(Map.of(name, value));
    }

    public static QueryParams ofString(Map.Entry<String, List<String>> value) {
        return ofString(Map.ofEntries(value));
    }

    public static QueryParams ofString(Map<String, List<String>> value) {
        return new QueryParams(convert(value));
    }


    public QueryParamValue getFirst(QueryParamName paramName) {
        return get(paramName).stream()
              .findFirst()
              .orElseThrow();
    }

    public Optional<QueryParamValue> findFirst(QueryParamName paramName) {
        return get(paramName).stream()
              .findFirst();
    }

    public static QueryParams empty() {
        return new QueryParams();
    }

    private static Map<QueryParamName, Set<QueryParamValue>> convert(Map<String, List<String>> queryParamMap) {
        return queryParamMap.entrySet().stream()
              .map(e -> Map.entry(
                    QueryParamName.of(e.getKey()),
                    e.getValue().stream().map(QueryParamValue::of).collect(Collectors.toSet())))
              .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
