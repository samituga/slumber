package io.samituga.bard.endpoint.request;

import io.samituga.bard.endpoint.type.PathParams;
import io.samituga.bard.endpoint.type.QueryParams;
import jakarta.servlet.http.HttpServletRequest;

public class HttpRequestBuilder {

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
        return new HttpRequest() {

            @Override
            public PathParams pathParams() {
                return pathParams;
            }

            @Override
            public QueryParams queryParams() {
                return queryParams;
            }

            @Override
            public HttpServletRequest request() {
                return request;
            }
        };
    }
}

