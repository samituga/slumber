package io.samituga.bard.fixture;

import static io.samituga.bard.endpoint.response.HttpResponseBuilder.httpResponseBuilder;

import io.samituga.bard.endpoint.response.HttpCode;
import io.samituga.bard.endpoint.response.HttpResponseBuilder;
import io.samituga.bard.endpoint.response.type.ByteResponseBody;
import io.samituga.slumber.ivern.http.type.Headers;
import jakarta.servlet.http.HttpServletResponse;

public class HttpResponseTestData {

    public static HttpResponseBuilder aResponse(HttpServletResponse response) {
        return httpResponseBuilder()
              .statusCode(HttpCode.OK)
              .headers(Headers.of("key", "value"))
              .response(response)
              .responseBody(ByteResponseBody.of("Hello World"));
    }
}
