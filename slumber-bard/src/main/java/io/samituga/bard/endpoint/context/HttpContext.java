package io.samituga.bard.endpoint.context;

import static io.samituga.slumber.heimer.validator.AssertionUtility.required;

import io.samituga.bard.endpoint.request.HttpRequest;
import io.samituga.bard.endpoint.response.HttpResponse;

public record HttpContext(HttpRequest request, HttpResponse response) {

    public HttpContext(HttpRequest request, HttpResponse response) {
        this.request = required("request", request);
        this.response = required("response", response);
    }
}
