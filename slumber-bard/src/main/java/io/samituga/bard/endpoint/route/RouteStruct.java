package io.samituga.bard.endpoint.route;

import static io.samituga.bard.endpoint.route.RouteBuilder.routeBuilder;
import static io.samituga.slumber.heimer.validator.AssertionUtility.required;

import io.samituga.bard.endpoint.context.HttpContext;
import io.samituga.bard.endpoint.request.HttpRequest;
import io.samituga.bard.endpoint.response.HttpResponse;
import io.samituga.bard.type.Path;
import java.util.function.Function;

record RouteStruct(Verb verb,
                   Path path,
                   Function<HttpContext, HttpContext> handler)
      implements Route {

    RouteStruct(Verb verb, Path path, Function<HttpContext, HttpContext> handler) {
        this.verb = required("verb", verb);
        this.path = required("path", path);
        this.handler = required("handler", handler);
    }

    @Override
    public RouteBuilder copy() {
        return routeBuilder()
              .verb(verb)
              .path(path)
              .handler(handler);
    }
}
