package io.samituga.bard.endpoint.response;

import static io.samituga.bard.endpoint.response.HttpResponseBuilder.httpResponseBuilder;
import static io.samituga.slumber.heimer.validator.AssertionUtility.required;

import io.samituga.slumber.ivern.http.type.Headers;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

record HttpResponseStruct(HttpCode statusCode,
                          Headers headers,
                          HttpServletResponse response,
                          Optional<ResponseBody> responseBody)
      implements HttpResponse {

    HttpResponseStruct(HttpCode statusCode,
                       Headers headers,
                       HttpServletResponse response,
                       Optional<ResponseBody> responseBody) {
        this.statusCode = required("statusCode", statusCode);
        this.headers = required("headers", headers);
        this.response = required("response", response);
        this.responseBody = required("responseBody", responseBody);
    }

    @Override
    public HttpResponseBuilder copy() {
        return httpResponseBuilder()
              .statusCode(statusCode)
              .headers(headers)
              .response(response)
              .responseBody(responseBody);
    }
}
