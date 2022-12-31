package io.samituga.bard.configuration;

/**
 * The server configuration data.
 */
public sealed interface ServerConfig permits ServerConfigImpl {

    static ServerConfigBuilder builder() {
        return new ServerConfigBuilder();
    }

    /**
     * Gets the server port.
     *
     * @return the server port
     */
    int port();
}
