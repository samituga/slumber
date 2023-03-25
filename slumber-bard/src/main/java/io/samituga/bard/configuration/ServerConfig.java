package io.samituga.bard.configuration;

import io.samituga.bard.endpoint.route.Route;
import io.samituga.bard.filter.Filter;
import io.samituga.slumber.ivern.builder.Structure;
import java.util.Collection;

public interface ServerConfig extends Structure<ServerConfig, ServerConfigBuilder> {

    int port();

    Collection<Filter> filters();

    Collection<Route> routes();
}
