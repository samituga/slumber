package io.samituga.bard.fixture;

import io.samituga.bard.endpoint.Request;
import io.samituga.bard.endpoint.Response;
import io.samituga.bard.endpoint.Route;
import io.samituga.bard.endpoint.Verb;
import io.samituga.bard.endpoint.type.Path;
import io.samituga.slumber.heimer.validator.AssertionUtility;
import java.util.function.Function;

public class RouteTestData {

    public static <T> RouteBuilder<T> aRoute() {
        return RouteTestData.<T>routeBuilder()
              .verb(Verb.GET)
              .path(Path.of("/hello"))
              .handler(req -> ResponseTestData.<T>defaultResponse().build());
    }

    public static <T> RouteBuilder<T> routeBuilder() {
        return new RouteBuilder<>();
    }

    public static class RouteBuilder<T> {

        private Verb verb;
        private Path path;
        Function<Request, Response<T>> handler;

        private RouteBuilder() {}

        public RouteBuilder<T> verb(Verb verb) {
            this.verb = verb;
            return this;
        }

        public RouteBuilder<T> path(Path path) {
            this.path = path;
            return this;
        }

        public RouteBuilder<T> handler(Function<Request, Response<T>> handler) {
            this.handler = handler;
            return this;
        }

        public Route<T> build() {
            return build(false);
        }

        public Route<T> build(boolean skipValidation) {
            if (!skipValidation) {
                validate(this);
            }
            return new Route<>() {
                @Override
                public Verb verb() {
                    return verb;
                }

                @Override
                public Path path() {
                    return path;
                }

                @Override
                public Function<Request, Response<T>> handler() {
                    return handler;
                }
            };
        }
    }

    private static void validate(RouteBuilder<?> builder) {
        validateVerb(builder.verb);
        validatePath(builder.path);
        validateHandler(builder.handler);
    }

    private static void validateVerb(Verb verb) {
        AssertionUtility.required("verb", verb);
    }

    private static void validatePath(Path path) {
        AssertionUtility.required("path", path);
    }

    private static void validateHandler(Function<Request, ? extends Response<?>> handler) {
        AssertionUtility.required("handler", handler);
    }
}
