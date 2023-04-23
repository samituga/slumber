package io.samituga.bard.endpoint.response;

import io.samituga.bard.endpoint.response.type.ByteResponseBody;
import io.samituga.bard.endpoint.response.type.InputStreamResponseBody;
import io.samituga.slumber.ivern.http.type.Headers;
import jakarta.servlet.http.HttpServletResponse;

public interface HttpResponse {

    HttpResponse statusCode(HttpCode statusCode);

    HttpResponse headers(Headers headers);

    HttpResponse responseBody(ByteResponseBody responseBody);
    HttpResponse responseBody(InputStreamResponseBody responseBody);

    HttpServletResponse response();

}
