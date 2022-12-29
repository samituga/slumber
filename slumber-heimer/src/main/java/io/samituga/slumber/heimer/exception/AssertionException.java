package io.samituga.slumber.heimer.exception;

/**
 * Unchecked exception thrown when there is a given input is not valid.
 */
public class AssertionException extends RuntimeException {

    public AssertionException(String message) {
        super(message);
    }

    public AssertionException(String message, Throwable cause) {
        super(message, cause);
    }

    public AssertionException(Throwable cause) {
        super(cause);
    }

    public AssertionException(String message, Throwable cause, boolean enableSuppression,
                              boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
