package io.samituga.bard.fixture;

import static io.samituga.bard.fixture.HttpResponseTestData.aResponse;
import static io.samituga.slumber.heimer.validator.AssertionUtility.required;

import io.samituga.bard.endpoint.request.HttpRequest;
import io.samituga.bard.endpoint.response.HttpResponse;
import io.samituga.bard.endpoint.Route;
import io.samituga.bard.endpoint.Verb;
import io.samituga.bard.endpoint.type.Path;
import java.util.function.Function;

public class RouteTestData {

    public static RouteBuilder aRoute() {
        return routeBuilder()
              .verb(Verb.GET)
              .path(Path.of("/hello"))
              .handler(req -> aResponse().build());
    }

    public static RouteBuilder routeBuilder() {
        return new RouteBuilder();
    }

    public static class RouteBuilder {

        private Verb verb;
        private Path path;
        Function<HttpRequest, HttpResponse> handler;

        private RouteBuilder() {}

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

        public Route build() {
            return build(false);
        }

        public Route build(boolean skipValidation) {
            if (!skipValidation) {
                validate(this);
            }
            return new Route() {
                @Override
                public Verb verb() {
                    return verb;
                }

                @Override
                public Path path() {
                    return path;
                }

                @Override
                public Function<HttpRequest, HttpResponse> handler() {
                    return handler;
                }
            };
        }
    }

    private static void validate(RouteBuilder builder) {
        validateVerb(builder.verb);
        validatePath(builder.path);
        validateHandler(builder.handler);
    }

    private static void validateVerb(Verb verb) {
        required("verb", verb);
    }

    private static void validatePath(Path path) {
        required("path", path);
    }

    private static void validateHandler(Function<HttpRequest, ? extends HttpResponse> handler) {
        required("handler", handler);
    }
}
