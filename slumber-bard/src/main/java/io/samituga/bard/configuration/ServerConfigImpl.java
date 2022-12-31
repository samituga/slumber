package io.samituga.bard.configuration;

import static io.samituga.slumber.heimer.validator.AssertionUtility.requiredValidPort;

record ServerConfigImpl(int port) implements ServerConfig {

    ServerConfigImpl(int port) {
        this.port = requiredValidPort(port);
    }
}
