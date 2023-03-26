package io.samituga.bard.endpoint.route;

import io.samituga.bard.endpoint.context.HttpContext;
import io.samituga.bard.type.Path;
import io.samituga.slumber.ivern.builder.Builder;
import java.util.function.Function;

public class RouteBuilder implements Builder<Route> {
    private Verb verb;
    private Path path;
    private Function<HttpContext, HttpContext> handler;

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

    public RouteBuilder handler(Function<HttpContext, HttpContext> handler) {
        this.handler = handler;
        return this;
    }

    @Override
    public Route build() {
        return new RouteStruct(verb, path, handler);
    }
}
