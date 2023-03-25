package io.samituga.bard.endpoint.route;

import static io.samituga.slumber.heimer.validator.AssertionUtility.required;

import io.samituga.bard.endpoint.request.HttpRequest;
import io.samituga.bard.endpoint.response.HttpResponse;
import io.samituga.bard.type.Path;
import java.util.function.Function;

record RouteImpl(Verb verb,
                 Path path,
                 Function<HttpRequest, HttpResponse> handler)
      implements Route {

    RouteImpl(Verb verb, Path path, Function<HttpRequest, HttpResponse> handler) {
        this.verb = required("verb", verb);
        this.path = required("path", path);
        this.handler = required("handler", handler);
    }
}
