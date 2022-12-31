package io.samituga.bard.application;


import io.samituga.bard.configuration.ServerConfig;

/**
 * Interface for the server operations.
 */
public interface SlumberApplication {


    /**
     * Initializes the server.
     *
     * @param config the configuration for the server
     * @throws io.samituga.bard.exception.ServerAlreadyInitializedException when the server was already initialized
     */
    void init(ServerConfig config);

    /**
     * Operations to perform on the shutdown of the server.
     *
     * @throws io.samituga.bard.exception.ServerAlreadyInitializedException when the server is not in pro
     */
    void shutdown();

}
