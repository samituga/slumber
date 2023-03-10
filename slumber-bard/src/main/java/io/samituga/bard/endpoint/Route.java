package io.samituga.bard.endpoint;

import io.samituga.bard.endpoint.type.Path;
import java.util.function.Function;

/**
 * @param <T> the type of the response.
 */
public interface Route<T> {

    Verb verb();

    Path path();

    Function<Request, Response<T>> handler();
}
