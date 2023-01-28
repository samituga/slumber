package io.samituga.slumber.rakan.support;

import io.samituga.slumber.ivern.http.type.Headers;
import io.samituga.slumber.rakan.helper.HeadersResolver;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public final class HttpClientSupport { // TODO: 2023-01-28 Naming

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);
    public static final HttpClient DEFAULT_HTTP_CLIENT;

    static {
        DEFAULT_HTTP_CLIENT = HttpClient.newBuilder()
              .followRedirects(HttpClient.Redirect.NORMAL)
              .connectTimeout(DEFAULT_TIMEOUT)
              .build();
    }

    public static HttpResponse<String> get(HttpClient client,
                                           URI uri)
          throws IOException, InterruptedException {
        return get(client, uri, Headers.empty());
    }

    public static HttpResponse<String> get(HttpClient client,
                                           URI uri,
                                           Headers headers)
          throws IOException, InterruptedException {
        return get(client, uri, headers, DEFAULT_TIMEOUT);
    }

    public static HttpResponse<String> get(HttpClient client,
                                           URI uri,
                                           Headers headers,
                                           Duration timeout)
          throws IOException, InterruptedException {
        return get(client, uri, headers, timeout, HttpResponse.BodyHandlers.ofString());
    }

    public static <T> HttpResponse<T> get(HttpClient client,
                                          URI uri,
                                          Headers headers,
                                          Duration timeout,
                                          HttpResponse.BodyHandler<T> responseBodyHandler)
          throws IOException, InterruptedException {

        var request = HttpRequest.newBuilder()
              .GET()
              .uri(uri)
              .timeout(timeout);

        if (!headers.isEmpty()) {
            request.headers(HeadersResolver.from(headers));
        }

        return execute(client, request.build(), responseBodyHandler);
    }

    private static <T> HttpResponse<T> execute(HttpClient client,
                                               HttpRequest request,
                                               HttpResponse.BodyHandler<T> responseBodyHandler)
          throws IOException, InterruptedException {

        // TODO: 2023-01-28 Logging
        // TODO: 2023-01-28 Handle error?
        return client.send(request, responseBodyHandler);
    }

    private static <T> CompletableFuture<HttpResponse<T>> executeAsync(HttpClient client,
                                                                       HttpResponse.BodyHandler<T> responseBodyHandler,
                                                                       HttpRequest request) {
        // TODO: 2023-01-28 Logging
        // TODO: 2023-01-28 Handle error?
        return client.sendAsync(request, responseBodyHandler);
    }
}
