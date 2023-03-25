package io.samituga.bard.configuration;

import io.samituga.bard.endpoint.route.Route;
import io.samituga.bard.filter.Filter;

import java.util.Collection;

/**
 * The server configuration data.
 */
public interface ServerConfig {

    /**
     * Gets the server port.
     *
     * @return the server port
     */
    int port();

    /**
     * Gets the web server filters.
     *
     * @return the filters
     */
    Collection<Filter> filters();

    /**
     * Gets the routes to be registered.
     *
     * @return the routes
     */
    Collection<Route> routes();
}
