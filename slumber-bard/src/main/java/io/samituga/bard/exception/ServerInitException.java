package io.samituga.bard.exception;

import io.samituga.bard.configuration.ServerConfig;

/**
 * Unchecked exception thrown when there is an attempt to
 * {@link io.samituga.bard.application.SlumberApplication#init(ServerConfig) init}
 * when the server is already initialized.
 */
public class ServerInitException extends RuntimeException {
    private static final String ERROR_MESSAGE = "The server is already online";

    public ServerInitException() {
        super(ERROR_MESSAGE);
    }
}
