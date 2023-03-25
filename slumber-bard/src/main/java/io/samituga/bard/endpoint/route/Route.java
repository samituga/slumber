package io.samituga.bard.endpoint.route;

import io.samituga.bard.endpoint.request.HttpRequest;
import io.samituga.bard.endpoint.response.HttpResponse;
import io.samituga.bard.type.Path;
import io.samituga.slumber.ivern.builder.Structure;
import java.util.function.Function;

public interface Route extends Structure<Route, RouteBuilder> {

    Verb verb();

    Path path();

    Function<HttpRequest, HttpResponse> handler();
}
