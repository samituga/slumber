package io.samituga.slumber.bard.javalin.mapper;

import io.javalin.http.Context;
import io.samituga.bard.endpoint.context.HttpContext;
import io.samituga.bard.endpoint.request.HttpRequestImpl;
import io.samituga.bard.endpoint.response.HttpResponseImpl;

public class HttpContextMapper {

    public static HttpContext toHttpContext(Context javalinCtx) {
        return new HttpContext(
              new HttpRequestImpl(javalinCtx.req(), javalinCtx.matchedPath()),
              new HttpResponseImpl(javalinCtx.res()));
    }
}
