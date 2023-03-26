package io.samituga.slumber.bard.javalin.mapper;

import io.javalin.http.Context;
import io.samituga.bard.endpoint.response.HttpCode;
import io.samituga.bard.endpoint.response.HttpResponse;
import io.samituga.bard.endpoint.response.HttpResponseBuilder;
import io.samituga.bard.endpoint.response.ResponseBody;
import io.samituga.slumber.ivern.http.type.Headers;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HttpResponseMapper {


    public static HttpResponse fromJavalinContext(Context ctx) {
        return fromJavalinContext(ctx, Optional.empty());
    }


    public static HttpResponse fromJavalinContext(Context ctx,
                                                  Optional<ResponseBody> responseBody) {
        return HttpResponseBuilder.httpResponseBuilder()
              .statusCode(HttpCode.fromStatusCode(ctx.statusCode()))
              .headers(getResponseHeaders(ctx.res()))
              .response(ctx.res())
              .responseBody(responseBody) // TODO: 2023-03-26 Should read body from HttpServletResponse?
              .build();
    }

    private static Headers getResponseHeaders(HttpServletResponse response) {
        Collection<String> headerNames = response.getHeaderNames();
        Map<String, String> headers = new HashMap<>();
        for (String headerName : headerNames) {
            String headerValue = response.getHeader(headerName);
            headers.put(headerName, headerValue);
        }
        return Headers.of(headers);
    }
}
