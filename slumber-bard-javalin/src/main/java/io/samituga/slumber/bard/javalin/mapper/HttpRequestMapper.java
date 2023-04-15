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

        return httpRequestBuilder()
              .pathParams(PathParams.ofString(ctx.pathParamMap()))
              .queryParams(QueryParams.ofString(ctx.queryParamMap()))
              .request(ctx.req())
              .build();
    }
}
