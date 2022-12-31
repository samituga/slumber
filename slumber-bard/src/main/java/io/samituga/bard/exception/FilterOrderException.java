package io.samituga.bard.exception;

/**
 * Unchecked exception to be thrown when there is more than one filter with
 * {@link io.samituga.bard.filter.Precedence#FIRST first precedence} or
 * {@link io.samituga.bard.filter.Precedence#LAST last precedence}
 */
public class FilterOrderException extends RuntimeException {

    private static final String ERROR_MESSAGE = "There can only be one filter with first precedence or last precedence";

    public FilterOrderException() {
        super(ERROR_MESSAGE);
    }
}
