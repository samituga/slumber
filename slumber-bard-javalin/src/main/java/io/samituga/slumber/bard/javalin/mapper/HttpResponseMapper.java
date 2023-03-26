package io.samituga.slumber.bard.javalin.mapper;

import io.javalin.http.Context;
import io.samituga.bard.endpoint.response.HttpCode;
import io.samituga.bard.endpoint.response.HttpResponse;
import io.samituga.bard.endpoint.response.HttpResponseBuilder;
import io.samituga.bard.endpoint.response.ResponseBody;
import io.samituga.slumber.ivern.http.type.Headers;
import java.util.Optional;

public class HttpResponseMapper {


    public static HttpResponse fromJavalinContext(Context ctx) {
        return fromJavalinContext(ctx, Optional.empty());
    }


    public static HttpResponse fromJavalinContext(Context ctx,
                                                  Optional<ResponseBody> responseBody) {
        return HttpResponseBuilder.httpResponseBuilder()
              .statusCode(HttpCode.fromStatusCode(ctx.statusCode()))
              .headers(Headers.of(ctx.headerMap()))
              .response(ctx.res())
              .responseBody(responseBody)
              .build();
    }
}
