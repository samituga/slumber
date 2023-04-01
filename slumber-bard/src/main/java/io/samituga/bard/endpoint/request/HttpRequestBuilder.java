package io.samituga.bard.endpoint.request;

import io.samituga.bard.endpoint.request.type.PathParams;
import io.samituga.bard.endpoint.request.type.QueryParams;
import io.samituga.bard.endpoint.request.type.RequestBody;
import io.samituga.slumber.ivern.builder.Builder;
import io.samituga.slumber.ivern.http.type.Headers;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;

public class HttpRequestBuilder implements Builder<HttpRequest> {

    private Headers headers;
    private PathParams pathParams;
    private QueryParams queryParams;
    private HttpServletRequest request;
    private Optional<RequestBody> requestBody = Optional.empty();

    private HttpRequestBuilder() {}

    public static HttpRequestBuilder httpRequestBuilder() {
        return new HttpRequestBuilder();
    }

    public HttpRequestBuilder headers(Headers headers) {
        this.headers = headers;
        return this;
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

    public HttpRequestBuilder requestBody(Optional<RequestBody> requestBody) {
        this.requestBody = requestBody;
        return this;
    }


    @Override
    public HttpRequest build() {
        return new HttpRequestStruct(headers, pathParams, queryParams, request, requestBody);
    }
}

