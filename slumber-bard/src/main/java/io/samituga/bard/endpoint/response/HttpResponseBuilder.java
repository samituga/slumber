package io.samituga.bard.endpoint.response;

import io.samituga.slumber.ivern.builder.Builder;
import io.samituga.slumber.ivern.http.type.Headers;

import java.util.Optional;

public class HttpResponseBuilder implements Builder<HttpResponse> {

    private HttpCode statusCode;
    private Headers headers = Headers.empty();
    private Optional<ResponseBody> responseBody = Optional.empty();

    private HttpResponseBuilder() {}

    public static HttpResponseBuilder httpResponseBuilder() {
        return new HttpResponseBuilder();
    }

    public HttpResponseBuilder statusCode(HttpCode statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public HttpResponseBuilder headers(Headers headers) {
        this.headers = headers;
        return this;
    }

    public HttpResponseBuilder responseBody(ResponseBody responseBody) {
        this.responseBody = Optional.ofNullable(responseBody);
        return this;
    }

    public HttpResponseBuilder responseBody(Optional<ResponseBody> responseBody) {
        this.responseBody = responseBody;
        return this;
    }

    @Override
    public HttpResponse build() {
        return new HttpResponseStruct(statusCode, headers, responseBody);
    }
}
