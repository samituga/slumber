package io.samituga.bard.endpoint;

import io.samituga.bard.endpoint.type.Headers;
import io.samituga.bard.endpoint.type.PathParams;
import io.samituga.bard.endpoint.type.QueryParams;

/**
 * Represents the information about an HTTP request.
 */
public interface Request {

    /**
     * The path params of the request.
     *
     * @return the path params of the request
     */
    PathParams pathParams();

    /**
     * The query params of the request.
     *
     * @return the query params of the request
     */
    QueryParams queryParams();

    /**
     * The headers of the request.
     *
     * @return the headers of the request
     */
    Headers headers();
}
