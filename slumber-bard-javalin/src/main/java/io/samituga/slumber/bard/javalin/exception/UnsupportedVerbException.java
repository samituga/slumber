package io.samituga.slumber.bard.javalin.exception;

import io.samituga.bard.endpoint.route.Verb;

public class UnsupportedVerbException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Javalin doesn't support %s verb";

    public UnsupportedVerbException(Verb verb) {
        super(String.format(ERROR_MESSAGE, verb.name()));
    }

    public void a() {

    }
}
