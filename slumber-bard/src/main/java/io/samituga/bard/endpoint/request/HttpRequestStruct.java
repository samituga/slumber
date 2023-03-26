package io.samituga.bard.endpoint.request;

import static io.samituga.bard.endpoint.request.HttpRequestBuilder.httpRequestBuilder;
import static io.samituga.slumber.heimer.validator.AssertionUtility.required;

import io.samituga.bard.endpoint.request.type.PathParams;
import io.samituga.bard.endpoint.request.type.QueryParams;
import io.samituga.bard.endpoint.request.type.RequestBody;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;

record HttpRequestStruct(PathParams pathParams,
                         QueryParams queryParams,
                         HttpServletRequest request,
                         Optional<RequestBody> requestBody)
      implements HttpRequest {

    HttpRequestStruct(PathParams pathParams,
                      QueryParams queryParams,
                      HttpServletRequest request,
                      Optional<RequestBody> requestBody) {
        this.pathParams = required("pathParams", pathParams);
        this.queryParams = required("queryParams", queryParams);
        this.request = required("request", request);
        this.requestBody = required("request", requestBody);
    }

    @Override
    public HttpRequestBuilder copy() {
        return httpRequestBuilder()
              .pathParams(pathParams)
              .queryParams(queryParams)
              .request(request)
              .requestBody(requestBody);
    }
}
