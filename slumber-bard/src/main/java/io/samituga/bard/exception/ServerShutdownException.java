package io.samituga.bard.exception;


/**
 * Unchecked exception thrown when there is an attempt to
 * {@link io.samituga.bard.application.SlumberApplication#shutdown() shutdown}
 * when the server is not online.
 */
public class ServerShutdownException extends RuntimeException {
    private static final String ERROR_MESSAGE = "The server is not online";


    public ServerShutdownException() {
        super(ERROR_MESSAGE);
    }
}
