package io.samituga.bard.endpoint.type;

import io.samituga.slumber.ivern.type.MapType;
import java.util.Map;

public class QueryParams extends MapType<QueryParamName, QueryParamValue> {

    private QueryParams() {
        super();
    }

    private QueryParams(Map<QueryParamName, QueryParamValue> value) {
        super(value);
    }


    public static QueryParams of(Map<QueryParamName, QueryParamValue> value) {
        return new QueryParams(value);
    }

    public static QueryParams of(Map.Entry<QueryParamName, QueryParamValue> value) {
        return new QueryParams(Map.ofEntries(value));
    }

    public static QueryParams of(QueryParamName name, QueryParamValue value) {
        return new QueryParams(Map.of(name, value));
    }

    public static QueryParams empty() {
        return new QueryParams();
    }
}
