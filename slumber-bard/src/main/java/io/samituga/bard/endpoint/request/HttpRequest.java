package io.samituga.bard.endpoint.request;

import io.samituga.bard.endpoint.type.PathParams;
import io.samituga.bard.endpoint.type.QueryParams;
import jakarta.servlet.http.HttpServletRequest;

public interface HttpRequest {

    PathParams pathParams();

    QueryParams queryParams();

    HttpServletRequest request();
}
