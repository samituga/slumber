package io.samituga.bard.endpoint.route;

import io.samituga.bard.endpoint.request.HttpRequest;
import io.samituga.bard.endpoint.response.HttpResponse;
import io.samituga.bard.type.Path;
import io.samituga.slumber.ivern.builder.Builder;
import java.util.function.Function;

public class RouteBuilder implements Builder<Route> {
    private Verb verb;
    private Path path;
    private Function<HttpRequest, HttpResponse> handler;

    private RouteBuilder() {}

    public static RouteBuilder routeBuilder() {
        return new RouteBuilder();
    }

    public RouteBuilder verb(Verb verb) {
        this.verb = verb;
        return this;
    }

    public RouteBuilder path(Path path) {
        this.path = path;
        return this;
    }

    public RouteBuilder handler(Function<HttpRequest, HttpResponse> handler) {
        this.handler = handler;
        return this;
    }

    @Override
    public Route build() {
        return new RouteStruct(verb, path, handler);
    }
}
