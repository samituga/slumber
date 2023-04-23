package io.samituga.slumber.bard.javalin.mapper;

import io.javalin.http.Context;
import io.samituga.bard.endpoint.context.HttpContext;
import io.samituga.bard.endpoint.context.HttpContextBuilder;
import io.samituga.bard.endpoint.request.HttpRequestImpl;
import io.samituga.bard.endpoint.response.HttpResponseImpl;

public class HttpContextMapper {


    public static HttpContext toHttpContext(Context javalinCtx) {

        return HttpContextBuilder.httpContextBuilder()
              .request(new HttpRequestImpl(javalinCtx.req(), javalinCtx.matchedPath()))
              .response(new HttpResponseImpl(javalinCtx.res()))
              .build();
    }
}
