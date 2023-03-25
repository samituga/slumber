package io.samituga.bard.fixture;

import static io.samituga.bard.endpoint.request.HttpRequestBuilder.httpRequestBuilder;

import io.samituga.bard.endpoint.request.HttpRequestBuilder;
import io.samituga.bard.endpoint.request.type.PathParamName;
import io.samituga.bard.endpoint.request.type.PathParamValue;
import io.samituga.bard.endpoint.request.type.PathParams;
import io.samituga.bard.endpoint.request.type.QueryParamName;
import io.samituga.bard.endpoint.request.type.QueryParamValue;
import io.samituga.bard.endpoint.request.type.QueryParams;

public class HttpRequestTestData {

    public static HttpRequestBuilder aRequest() {
        return requestBuilder()
              .pathParams(PathParams.of(PathParamName.of("name"), PathParamValue.of("value")))
              .queryParams(QueryParams.of(QueryParamName.of("query"), QueryParamValue.of("Qvalue")));
    }

    public static HttpRequestBuilder requestBuilder() {
        return httpRequestBuilder();
    }
}
