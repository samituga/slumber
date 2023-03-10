package io.samituga.bard.endpoint.type;

import io.samituga.slumber.ivern.type.MapType;
import java.util.Map;
import java.util.Set;

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

    public static QueryParams empty() {
        return new QueryParams();
    }
}
