package io.samituga.bard.endpoint.request;

import static io.samituga.bard.endpoint.request.HttpRequestBuilder.httpRequestBuilder;
import static io.samituga.slumber.heimer.validator.AssertionUtility.required;

import io.samituga.bard.endpoint.request.type.PathParams;
import io.samituga.bard.endpoint.request.type.QueryParams;
import jakarta.servlet.http.HttpServletRequest;

record HttpRequestStruct(PathParams pathParams,
                         QueryParams queryParams,
                         HttpServletRequest request)
      implements HttpRequest {

    HttpRequestStruct(PathParams pathParams,
                      QueryParams queryParams,
                      HttpServletRequest request) {
        this.pathParams = required("pathParams", pathParams);
        this.queryParams = required("queryParams", queryParams);
        this.request = required("request", request);
    }

    @Override
    public HttpRequestBuilder copy() {
        return httpRequestBuilder()
              .pathParams(pathParams)
              .queryParams(queryParams)
              .request(request);
    }
}
