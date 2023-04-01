package io.samituga.bard.fixture;

import static io.samituga.bard.endpoint.request.HttpRequestBuilder.httpRequestBuilder;

import io.samituga.bard.endpoint.request.HttpRequestBuilder;
import io.samituga.bard.endpoint.request.type.PathParamName;
import io.samituga.bard.endpoint.request.type.PathParamValue;
import io.samituga.bard.endpoint.request.type.PathParams;
import io.samituga.bard.endpoint.request.type.QueryParamName;
import io.samituga.bard.endpoint.request.type.QueryParamValue;
import io.samituga.bard.endpoint.request.type.QueryParams;
import io.samituga.bard.endpoint.request.type.RequestBody;
import io.samituga.slumber.ivern.http.type.Headers;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;

public class HttpRequestTestData {

    // TODO: 2023-03-25 how to build with HttpServeletRequest?

    public static HttpRequestBuilder aRequest(HttpServletRequest request) {
        return httpRequestBuilder()
              .headers(Headers.of("Header-Key", "Header-Value"))
              .pathParams(PathParams.of(PathParamName.of("name"), PathParamValue.of("value")))
              .queryParams(QueryParams.of(QueryParamName.of("query"), QueryParamValue.of("Qvalue")))
              .request(request)
              .requestBody(Optional.of(RequestBody.of("body")));
    }
}
