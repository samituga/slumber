package io.samituga.bard.endpoint.request;

import io.samituga.bard.endpoint.request.type.PathParams;
import io.samituga.bard.endpoint.request.type.QueryParams;
import io.samituga.slumber.ivern.builder.Builder;
import jakarta.servlet.http.HttpServletRequest;

public class HttpRequestBuilder implements Builder<HttpRequest> {

    private PathParams pathParams;
    private QueryParams queryParams;
    private HttpServletRequest request;

    private HttpRequestBuilder() {}

    public static HttpRequestBuilder httpRequestBuilder() {
        return new HttpRequestBuilder();
    }

    public HttpRequestBuilder pathParams(PathParams pathParams) {
        this.pathParams = pathParams;
        return this;
    }

    public HttpRequestBuilder queryParams(QueryParams queryParams) {
        this.queryParams = queryParams;
        return this;
    }

    public HttpRequestBuilder request(HttpServletRequest request) {
        this.request = request;
        return this;
    }


    public HttpRequest build() {
        return new HttpRequestImpl(pathParams, queryParams, request);
    }
}

