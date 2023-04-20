package io.samituga.slumber.bard.javalin.mapper;

import io.javalin.http.Context;
import io.samituga.bard.endpoint.request.HttpRequest;
import io.samituga.bard.endpoint.request.HttpRequestImpl;
import io.samituga.bard.endpoint.request.type.QueryParams;

public class HttpRequestMapper {

    public static HttpRequest fromJavalinContext(Context ctx) {

        return new HttpRequestImpl(ctx.req(), ctx.matchedPath());
    }
}
