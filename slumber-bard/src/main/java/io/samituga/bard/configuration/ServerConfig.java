package io.samituga.bard.configuration;

import io.samituga.bard.filter.Filter;

import java.util.Collection;
import java.util.Optional;

/**
 * The server configuration data.
 */
public interface ServerConfig {

    /**
     * Gets the server port.
     *
     * @return the server port
     */
    Optional<Integer> port();

    /**
     * Gets the web server filters.
     *
     * @return the filters
     */
    Optional<Collection<Filter>> filters();
}
