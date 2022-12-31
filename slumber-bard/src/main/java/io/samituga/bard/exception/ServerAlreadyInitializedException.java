package io.samituga.bard.exception;

import io.samituga.bard.configuration.ServerConfig;

/**
 * Unchecked exception thrown when there is an attempt to
 * {@link io.samituga.bard.application.SlumberApplication#init(ServerConfig) init}
 * when the server is already initialized.
 */
public class ServerAlreadyInitializedException extends RuntimeException {

    private static final String ERROR_MESSAGE = "The server is already in process";

    public ServerAlreadyInitializedException() {
        super(ERROR_MESSAGE);
    }
}
