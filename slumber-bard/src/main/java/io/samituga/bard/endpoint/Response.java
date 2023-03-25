package io.samituga.bard.endpoint;

import io.samituga.slumber.ivern.http.type.Headers;
import java.util.Optional;

/**
 * Represents an HTTP response.
 */
public interface Response {

    HttpCode statusCode();

    Optional<ResponseBody> responseBody();

    Headers headers();

}
