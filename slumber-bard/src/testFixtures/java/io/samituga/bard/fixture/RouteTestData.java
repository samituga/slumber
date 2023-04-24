package io.samituga.bard.fixture;

import static io.samituga.bard.endpoint.route.RouteBuilder.routeBuilder;
import static io.samituga.bard.fixture.HttpResponseTestData.aResponseConsumer;

import io.samituga.bard.endpoint.route.RouteBuilder;
import io.samituga.bard.endpoint.route.Verb;
import io.samituga.bard.type.Path;

public class RouteTestData {

    public static RouteBuilder aRoute() {
        return routeBuilder()
              .verb(Verb.GET)
              .path(Path.of("/hello"))
              .handler(aResponseConsumer());
    }
}
