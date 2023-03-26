package io.samituga.bard.fixture;

import static io.samituga.bard.endpoint.route.RouteBuilder.routeBuilder;
import static io.samituga.bard.fixture.HttpResponseTestData.aResponse;

import io.samituga.bard.endpoint.route.RouteBuilder;
import io.samituga.bard.endpoint.route.Verb;
import io.samituga.bard.type.Path;
import jakarta.servlet.http.HttpServletResponse;

public class RouteTestData {

    public static RouteBuilder aRoute(HttpServletResponse response) {
        return routeBuilder()
              .verb(Verb.GET)
              .path(Path.of("/hello"))
              .handler(ctx -> ctx.withResponse(aResponse(response).build()));
    }
}
