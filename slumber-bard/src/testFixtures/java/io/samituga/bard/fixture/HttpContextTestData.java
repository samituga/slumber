package io.samituga.bard.fixture;

import static io.samituga.bard.endpoint.context.HttpContextBuilder.httpContextBuilder;

import io.samituga.bard.endpoint.context.HttpContextBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HttpContextTestData {

    public static HttpContextBuilder aHttpContext(HttpServletRequest request,
                                                  HttpServletResponse response) {
        return httpContextBuilder()
              .request(HttpRequestTestData.aRequest(request).build())
              .response(HttpResponseTestData.aResponse(response).build());
    }
}
