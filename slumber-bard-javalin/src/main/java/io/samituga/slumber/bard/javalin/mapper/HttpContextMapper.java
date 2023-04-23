package io.samituga.slumber.bard.javalin.mapper;

import io.javalin.http.Context;
import io.samituga.bard.endpoint.context.HttpContext;
import io.samituga.bard.endpoint.context.HttpContextBuilder;
import io.samituga.bard.endpoint.request.HttpRequest;
import io.samituga.bard.endpoint.request.HttpRequestImpl;
import io.samituga.bard.endpoint.response.HttpResponse;
import io.samituga.bard.endpoint.response.HttpResponseImpl;

public class HttpContextMapper {


    public static HttpContext fromJavalinContext(Context javalinCtx) {

        HttpRequest request = new HttpRequestImpl(javalinCtx.req(), javalinCtx.matchedPath());
        HttpResponse response = new HttpResponseImpl(javalinCtx.res());

        return HttpContextBuilder.httpContextBuilder()
              .request(request)
              .response(response)
              .build();
    }
}
