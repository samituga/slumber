package io.samituga.slumber.bard.javalin.mapper;

import io.javalin.http.Context;
import io.samituga.bard.endpoint.context.HttpContext;
import io.samituga.bard.endpoint.context.HttpContextBuilder;
import io.samituga.bard.endpoint.request.HttpRequest;
import io.samituga.bard.endpoint.response.HttpResponse;

public class HttpContextMapper {


    public static HttpContext fromJavalinContext(Context context) {

        HttpRequest request = HttpRequestMapper.fromJavalinContext(context);
        HttpResponse response = HttpResponseMapper.fromJavalinContext(context);

        return HttpContextBuilder.httpContextBuilder()
              .request(request)
              .response(response)
              .build();
    }
}
