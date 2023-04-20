package io.samituga.slumber.heimer.error;

/**
 * Error thrown when there is an attempt to instantiate a utility class.
 */
public class UtilityClassInstantiationError extends Error {

    public static final String UTILITY_CLASS_ERROR_FORMAT = "Cannot instantiate %s utility class.";

    public UtilityClassInstantiationError(String message) {
        super(message);
    }

    public UtilityClassInstantiationError(String message, Throwable cause) {
        super(message, cause);
    }

    public UtilityClassInstantiationError(Throwable cause) {
        super(cause);
    }

    public UtilityClassInstantiationError(String message, Throwable cause,
                                          boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
