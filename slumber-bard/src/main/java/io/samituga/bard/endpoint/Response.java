package io.samituga.bard.endpoint;

import io.samituga.slumber.ivern.http.type.Headers;
import java.util.Optional;

/**
 * Represents an HTTP response.
 *
 * @param <T> the type of the response
 */
public interface Response<T> {

    HttpCode statusCode();

    Optional<T> responseBody();

    Headers headers();

}
