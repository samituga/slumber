package io.samituga.slumber.bard.javalin.mapper;

import static io.samituga.bard.endpoint.request.HttpRequestBuilder.httpRequestBuilder;

import io.javalin.http.Context;
import io.samituga.bard.endpoint.request.HttpRequest;
import io.samituga.bard.endpoint.request.type.PathParams;
import io.samituga.bard.endpoint.request.type.QueryParams;
import io.samituga.bard.endpoint.request.type.RequestBody;
import io.samituga.slumber.ivern.http.type.Headers;
import java.util.Optional;

public class HttpRequestMapper {

    public static HttpRequest fromJavalinContext(Context ctx) {

        var requestBody = getRequestBody(ctx);

        return httpRequestBuilder()
              .headers(Headers.of(ctx.headerMap()))
              .pathParams(PathParams.ofString(ctx.pathParamMap()))
              .queryParams(QueryParams.ofString(ctx.queryParamMap()))
              .request(ctx.req())
              .requestBody(requestBody)
              .build();
    }

    private static Optional<RequestBody> getRequestBody(Context ctx) {
        byte[] bytes = ctx.bodyAsBytes();

        if (bytes == null || bytes.length == 0) {
            return Optional.empty();
        }
        return Optional.of(RequestBody.of(bytes));
    }
}
