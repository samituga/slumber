package io.samituga.bard.endpoint.route;

import io.samituga.bard.endpoint.context.HttpContext;
import io.samituga.bard.type.Path;
import io.samituga.slumber.ivern.structure.Structure;
import java.util.function.Consumer;
import java.util.function.Function;

public interface Route extends Structure<Route, RouteBuilder> {

    Verb verb();

    Path path();

    Consumer<HttpContext> handler();
}
