package io.samituga.slumber.bard.javalin.stub;

import static io.samituga.slumber.bard.javalin.stub.StubServer.PATH_HELLO_WORLD;
import static io.samituga.slumber.bard.javalin.stub.StubServer.PATH_POST_TITLE;
import static io.samituga.slumber.bard.javalin.stub.StubServer.PORT;
import static io.samituga.slumber.rakan.request.HttpRequestBuilderImpl.DEFAULT_HTTP_CLIENT;

import io.samituga.slumber.rakan.request.HttpRequestBuilder;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;
import java.util.UUID;

public class StubClient {

    private static final String BASE_URI = "http://localhost:" + PORT;

    public HttpResponse<String> getTitle(UUID uuid) throws IOException, InterruptedException {
        return HttpRequestBuilder.request(String.class)
              .client(DEFAULT_HTTP_CLIENT)
              .uri(URI.create(BASE_URI + "/title/" + uuid))
              .responseBodyHandler(HttpResponse.BodyHandlers.ofString())
              .httpGet()
              .execute();
    }

    public HttpResponse<String> getTitlesByQuery(String firstLetter, boolean ignoreCase)
          throws IOException, InterruptedException {
        return HttpRequestBuilder.request(String.class)
              .client(DEFAULT_HTTP_CLIENT)
              .uri(URI.create(
                    BASE_URI + "/title?firstLetter=" + firstLetter + "&ignoreCase=" + ignoreCase))
              .responseBodyHandler(HttpResponse.BodyHandlers.ofString())
              .httpGet()
              .execute();
    }

    public HttpResponse<String> postTitle(String title) throws IOException, InterruptedException {
        return HttpRequestBuilder.request(String.class)
              .client(DEFAULT_HTTP_CLIENT)
              .uri(URI.create(BASE_URI + PATH_POST_TITLE))
              .responseBodyHandler(HttpResponse.BodyHandlers.ofString())
              .httpPost(title.getBytes())
              .execute();
    }

    public HttpResponse<String> deleteTitle(UUID uuid) throws IOException, InterruptedException {
        return HttpRequestBuilder.request(String.class)
              .client(DEFAULT_HTTP_CLIENT)
              .uri(URI.create(BASE_URI + "/auth/title/" + uuid))
              .responseBodyHandler(HttpResponse.BodyHandlers.ofString())
              .httpDelete()
              .execute();
    }

    public HttpResponse<String> getHelloWorld() throws IOException, InterruptedException {
        return HttpRequestBuilder.request(String.class)
              .client(DEFAULT_HTTP_CLIENT)
              .uri(URI.create(BASE_URI + PATH_HELLO_WORLD))
              .responseBodyHandler(HttpResponse.BodyHandlers.ofString())
              .httpGet()
              .execute();
    }
}
