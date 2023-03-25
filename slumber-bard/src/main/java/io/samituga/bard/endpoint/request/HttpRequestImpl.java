package io.samituga.bard.endpoint.request;

import static io.samituga.slumber.heimer.validator.AssertionUtility.required;

import io.samituga.bard.endpoint.type.PathParams;
import io.samituga.bard.endpoint.type.QueryParams;
import jakarta.servlet.http.HttpServletRequest;

public record HttpRequestImpl(PathParams pathParams,
                              QueryParams queryParams,
                              HttpServletRequest request)
      implements HttpRequest {

    public HttpRequestImpl(PathParams pathParams,
                           QueryParams queryParams,
                           HttpServletRequest request) {
        this.pathParams = required("pathParams", pathParams);
        this.queryParams = required("queryParams", queryParams);
        this.request = required("request", request);
    }
}
