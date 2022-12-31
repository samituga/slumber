package io.samituga.bard.application;


import io.samituga.bard.configuration.ServerConfig;
import io.samituga.bard.exception.ServerInitException;
import io.samituga.bard.exception.ServerShutdownException;

/**
 * Interface for the server operations.
 */
public interface SlumberApplication {


    /**
     * Initializes the server.
     *
     * @param config the configuration for the server
     * @throws ServerInitException when the server was already initialized
     */
    void init(ServerConfig config);

    /**
     * Operations to perform on the shutdown of the server.
     *
     * @throws ServerShutdownException when the server is not online
     */
    void shutdown();

}
