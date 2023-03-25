package io.samituga.bard.endpoint.request.type;

import io.samituga.slumber.ivern.type.Type;

public class QueryParamName extends Type<String> {

    private QueryParamName(String value) {
        super(value);
    }

    public static QueryParamName of(String value) {
        return new QueryParamName(value);
    }
}
