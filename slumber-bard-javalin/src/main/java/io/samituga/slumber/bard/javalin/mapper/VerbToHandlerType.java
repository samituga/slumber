package io.samituga.slumber.bard.javalin.mapper;

import io.javalin.http.HandlerType;
import io.samituga.bard.endpoint.route.Verb;
import io.samituga.slumber.bard.javalin.exception.UnsupportedVerbException;

public class VerbToHandlerType {
    public static HandlerType toHandlerType(Verb verb) {
        if (verb == Verb.PATH) {
            throw new UnsupportedVerbException(verb);
        }
        return HandlerType.valueOf(verb.name());
    }
}
