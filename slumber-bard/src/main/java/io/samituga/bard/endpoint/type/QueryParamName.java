package io.samituga.bard.endpoint.type;

import io.samituga.slumber.heimer.type.Type;

public class QueryParamName extends Type<String> {
    private QueryParamName(String value) {
        super(value);
    }

    public static QueryParamName of(String value) {
        return new QueryParamName(value);
    }
}
