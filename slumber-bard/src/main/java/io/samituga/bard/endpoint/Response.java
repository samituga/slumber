package io.samituga.bard.endpoint;

import io.samituga.bard.endpoint.type.ResponseBody;
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
