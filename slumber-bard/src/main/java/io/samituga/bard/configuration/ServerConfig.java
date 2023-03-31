package io.samituga.bard.configuration;

import io.samituga.bard.endpoint.route.Route;
import io.samituga.bard.filter.Filter;
import io.samituga.bard.handler.ExceptionHandler;
import io.samituga.bard.type.Port;
import io.samituga.slumber.ivern.structure.Structure;
import java.util.Collection;

public interface ServerConfig extends Structure<ServerConfig, ServerConfigBuilder> {

    Port port();

    Collection<Filter> filters();

    Collection<Route> routes();

    Collection<ExceptionHandler<? extends Exception>> exceptionHandlers();
}
