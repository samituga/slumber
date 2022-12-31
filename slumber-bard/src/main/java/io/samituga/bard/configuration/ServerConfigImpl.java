package io.samituga.bard.configuration;

import io.samituga.bard.filter.Filter;

import java.util.Collection;
import java.util.Optional;

import static io.samituga.slumber.heimer.validator.AssertionUtility.requiredValidPort;

record ServerConfigImpl(int port, Optional<Collection<Filter>> filters) implements ServerConfig {

    ServerConfigImpl(int port, Optional<Collection<Filter>> filters) {
        this.port = requiredValidPort(port);
        this.filters = filters;
    }
}
