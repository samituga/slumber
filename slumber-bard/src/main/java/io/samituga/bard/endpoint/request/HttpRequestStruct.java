package io.samituga.bard.endpoint.request;

import static io.samituga.bard.endpoint.request.HttpRequestBuilder.httpRequestBuilder;

import io.samituga.bard.endpoint.request.type.PathParams;
import io.samituga.bard.endpoint.request.type.QueryParams;
import io.samituga.bard.endpoint.request.type.RequestBody;

import static io.samituga.slumber.heimer.validator.AssertionUtility.required;

import io.samituga.slumber.ivern.http.type.Headers;
import io.samituga.slumber.ivern.io.IoUtils;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Optional;

record HttpRequestStruct(Headers headers,
                         PathParams pathParams,
                         QueryParams queryParams,
                         HttpServletRequest request)
      implements HttpRequest {

    HttpRequestStruct(Headers headers,
                      PathParams pathParams,
                      QueryParams queryParams,
                      HttpServletRequest request) {
        this.headers = required("headers", headers);
        this.pathParams = required("pathParams", pathParams);
        this.queryParams = required("queryParams", queryParams);
        this.request = required("request", request);
    }


    @Override
    public Optional<RequestBody> requestBody() {
        try {
            byte[] bytes = IoUtils.toBytes(request.getInputStream());
            return bytes.length > 0 ? Optional.of(RequestBody.of(bytes)) : Optional.empty();
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read the request body", e);
        }
    }

    @Override
    public HttpRequestBuilder copy() {
        return httpRequestBuilder()
              .headers(headers)
              .pathParams(pathParams)
              .queryParams(queryParams)
              .request(request);
    }
}
