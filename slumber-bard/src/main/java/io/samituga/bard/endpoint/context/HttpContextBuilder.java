package io.samituga.bard.endpoint.context;

import io.samituga.bard.endpoint.request.HttpRequest;
import io.samituga.bard.endpoint.response.HttpResponse;
import io.samituga.slumber.ivern.builder.Builder;

public class HttpContextBuilder implements Builder<HttpContext> {

    private HttpRequest request;
    private HttpResponse response;

    private HttpContextBuilder() {
    }

    public static HttpContextBuilder httpContextBuilder(){
        return new HttpContextBuilder();
    }

    public HttpContextBuilder request(HttpRequest request) {
        this.request = request;
        return this;
    }

    public HttpContextBuilder response(HttpResponse response) {
        this.response = response;
        return this;
    }

    @Override
    public HttpContext build() {
        return new HttpContextStruct(request, response);
    }
}
