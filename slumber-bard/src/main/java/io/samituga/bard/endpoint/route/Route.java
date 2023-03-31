package io.samituga.bard.endpoint.route;

import io.samituga.bard.endpoint.context.HttpContext;
import io.samituga.bard.type.Path;
import io.samituga.slumber.ivern.structure.Structure;
import java.util.function.Function;

public interface Route extends Structure<Route, RouteBuilder> {

    Verb verb();

    Path path();

    Function<HttpContext, HttpContext> handler(); // TODO: 2023-03-26 Can be of Consumer if HttpContext is mutable
}
