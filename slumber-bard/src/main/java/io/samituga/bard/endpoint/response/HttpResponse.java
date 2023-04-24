package io.samituga.bard.endpoint.response;

import io.samituga.slumber.ivern.http.type.Headers;
import jakarta.servlet.http.HttpServletResponse;

public interface HttpResponse {

    HttpResponse statusCode(HttpCode statusCode);

    HttpResponse headers(Headers headers);

    HttpResponse body(ResponseBody responseBody);

    HttpServletResponse response();
}
