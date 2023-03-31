package io.samituga.bard.endpoint.context;

import static io.samituga.bard.endpoint.context.HttpContextBuilder.httpContextBuilder;
import static io.samituga.slumber.heimer.validator.AssertionUtility.required;

import io.samituga.bard.endpoint.request.HttpRequest;
import io.samituga.bard.endpoint.response.HttpResponse;

record HttpContextStruct(HttpRequest request, HttpResponse response) implements HttpContext {

    HttpContextStruct(HttpRequest request, HttpResponse response) {
        this.request = required("request", request);
        this.response = required("response", response);
    }

    @Override
    public HttpContextBuilder copy() {
        return httpContextBuilder()
              .request(request)
              .response(response);
    }

    @Override
    public HttpContext withResponse(HttpResponse response) {
        return copy()
              .response(response)
              .build();
    }
}
