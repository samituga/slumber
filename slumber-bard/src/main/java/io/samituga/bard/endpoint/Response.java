package io.samituga.bard.endpoint;

import java.util.Optional;

/**
 * Represents an HTTP response.
 *
 * @param <T> the type of the response
 */
public interface Response<T> {

    /**
     * The status code of the response.
     *
     * @return the status code of the response
     */
    HttpCode statusCode();


    /**
     * The response body.
     *
     * @return the response body
     */
    Optional<T> responseBody();

}
