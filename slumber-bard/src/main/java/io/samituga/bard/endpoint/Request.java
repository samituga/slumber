package io.samituga.bard.endpoint;

import io.samituga.bard.endpoint.type.PathParams;
import io.samituga.bard.endpoint.type.QueryParams;
import jakarta.servlet.http.HttpServletRequest;

/**
 * HTTP request.
 */
public interface Request {

    PathParams pathParams();

    QueryParams queryParams();

    HttpServletRequest request();
}
