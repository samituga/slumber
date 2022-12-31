package io.samituga.bard.exception;

/**
 * Unchecked exception thrown when the server was not initialized yet.
 */
public class ServerNotInitialized extends RuntimeException {
    private static final String ERROR_MESSAGE = "The server is not operational";


    public ServerNotInitialized() {
        super(ERROR_MESSAGE);
    }
}
