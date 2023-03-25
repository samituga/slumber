package io.samituga.bard.endpoint.request.type;

import io.samituga.slumber.ivern.type.Type;

public class QueryParamValue extends Type<String> {

    private QueryParamValue(String value) {
        super(value);
    }

    public static QueryParamValue of(String value) {
        return new QueryParamValue(value);
    }
}
