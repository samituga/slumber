package io.samituga.bard.endpoint.response;

import static io.samituga.bard.endpoint.response.HttpResponseBuilder.httpResponseBuilder;
import static io.samituga.slumber.heimer.validator.AssertionUtility.required;

import io.samituga.slumber.ivern.http.type.Headers;
import java.util.Optional;

record HttpResponseImpl(HttpCode statusCode,
                        Headers headers,
                        Optional<ResponseBody> responseBody)
      implements HttpResponse {

    HttpResponseImpl(HttpCode statusCode,
                     Headers headers,
                     Optional<ResponseBody> responseBody) {
        this.statusCode = required("statusCode", statusCode);
        this.headers = required("headers", headers);
        this.responseBody = required("responseBody", responseBody);
    }

    @Override
    public HttpResponseBuilder copy() {
        return httpResponseBuilder()
              .statusCode(statusCode)
              .headers(headers)
              .responseBody(responseBody);
    }
}
