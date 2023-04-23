package io.samituga.bard.fixture;

import io.samituga.bard.endpoint.context.HttpContext;
import io.samituga.bard.endpoint.response.HttpCode;
import io.samituga.bard.endpoint.response.type.ByteResponseBody;
import io.samituga.slumber.ivern.http.type.Headers;

import java.util.function.Consumer;

public class HttpResponseTestData {

    public static Consumer<HttpContext> aResponseConsumer() {
        return ctx -> ctx.response()
              .statusCode(HttpCode.OK)
              .headers(Headers.of("key", "value"))
              .responseBody(ByteResponseBody.of("Hello World"));
    }
}
