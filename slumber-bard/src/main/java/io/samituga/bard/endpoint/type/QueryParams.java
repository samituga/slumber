package io.samituga.bard.endpoint.type;

import io.samituga.slumber.ivern.type.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class QueryParams extends Type<Map<String, List<String>>> {

    private QueryParams(Map<String, List<String>> value) {
        super(value);
    }

    public static QueryParams of(Map<String, List<String>> value) {
        return new QueryParams(Map.copyOf(value));
    }

    public static QueryParams of(Map.Entry<String, List<String>> value) {
        return new QueryParams(Map.ofEntries(value));
    }

    public static QueryParams empty() {
        return new QueryParams(Collections.emptyMap());
    }
}
