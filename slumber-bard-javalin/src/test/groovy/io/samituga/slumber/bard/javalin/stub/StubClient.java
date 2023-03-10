package io.samituga.slumber.bard.javalin.stub;

import static io.samituga.slumber.bard.javalin.stub.StubServer.PATH_HELLO_WORLD;
import static io.samituga.slumber.bard.javalin.stub.StubServer.PORT;

import io.samituga.slumber.rakan.request.HttpRequestBuilder;
import io.samituga.slumber.rakan.request.HttpRequestBuilderImpl;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;
import java.util.UUID;

public class StubClient {

    private static final String BASE_URI = "http://localhost:" + PORT;

    public HttpResponse<String> getTitle(UUID uuid) throws IOException, InterruptedException {
        return HttpRequestBuilder.request(String.class)
              .client(HttpRequestBuilderImpl.DEFAULT_HTTP_CLIENT)
              .uri(URI.create(BASE_URI + "/title/" + uuid.toString()))
              .responseBodyHandler(HttpResponse.BodyHandlers.ofString())
              .httpGet()
              .execute();
    }

    public HttpResponse<String> getHelloWorld() throws IOException, InterruptedException {
        return HttpRequestBuilder.request(String.class)
              .client(HttpRequestBuilderImpl.DEFAULT_HTTP_CLIENT)
              .uri(URI.create(BASE_URI + PATH_HELLO_WORLD))
              .responseBodyHandler(HttpResponse.BodyHandlers.ofString())
              .httpGet()
              .execute();
    }
}
