package io.samituga.bard.endpoint.response;

import static io.samituga.slumber.heimer.validator.AssertionUtility.required;

import io.samituga.bard.endpoint.HttpCode;
import io.samituga.slumber.ivern.http.type.Headers;
import java.util.Optional;

public record HttpResponseImpl(HttpCode statusCode,
                               Headers headers,
                               Optional<ResponseBody> responseBody)
      implements HttpResponse {

    public HttpResponseImpl(HttpCode statusCode,
                            Headers headers,
                            Optional<ResponseBody> responseBody) {
        this.statusCode = required("statusCode", statusCode);
        this.headers = required("headers", headers);
        this.responseBody = required("responseBody", responseBody);
    }
}
