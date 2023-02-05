package io.samituga.slumber.rakan.support;

import io.samituga.slumber.ivern.http.type.Headers;
import io.samituga.slumber.rakan.helper.HeadersResolver;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public final class HttpClientSupport { // TODO: 2023-01-28 Naming

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);
    public static final HttpClient DEFAULT_HTTP_CLIENT;

    static {
        DEFAULT_HTTP_CLIENT = HttpClient.newBuilder()
              .followRedirects(HttpClient.Redirect.NORMAL)
              .connectTimeout(DEFAULT_TIMEOUT)
              .build();
    }

    public static HttpExecutor<String> get(HttpClient client,
                                           URI uri)
          throws IOException, InterruptedException {
        return get(client, uri, Headers.empty());
    }

    public static HttpExecutor<String> get(HttpClient client,
                                           URI uri,
                                           Headers headers)
          throws IOException, InterruptedException {
        return get(client, uri, headers, DEFAULT_TIMEOUT);
    }

    public static HttpExecutor<String> get(HttpClient client,
                                           URI uri,
                                           Headers headers,
                                           Duration timeout)
          throws IOException, InterruptedException {
        return get(client, uri, headers, timeout, HttpResponse.BodyHandlers.ofString());
    }

    public static <T> HttpExecutor<T> get(HttpClient client,
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

        return HttpExecutor.executor(client, request.build(), responseBodyHandler);
    }

    public static <T> HttpExecutor<T> post(HttpClient client,
                                           URI uri,
                                           byte[] body,
                                           HttpResponse.BodyHandler<T> responseBodyHandler)
          throws IOException, InterruptedException {

        return post(client, uri, body, responseBodyHandler, Headers.empty(), DEFAULT_TIMEOUT);
    }

    public static <T> HttpExecutor<T> post(HttpClient client,
                                           URI uri,
                                           byte[] body,
                                           HttpResponse.BodyHandler<T> responseBodyHandler,
                                           Headers headers)
          throws IOException, InterruptedException {

        return post(client, uri, body, responseBodyHandler, headers, DEFAULT_TIMEOUT);
    }

    public static <T> HttpExecutor<T> post(HttpClient client,
                                           URI uri,
                                           byte[] body,
                                           HttpResponse.BodyHandler<T> responseBodyHandler,
                                           Headers headers,
                                           Duration timeout)
          throws IOException, InterruptedException {

        var request = HttpRequest.newBuilder()
              .POST(HttpRequest.BodyPublishers.ofByteArray(body))
              .uri(uri)
              .timeout(timeout);

        if (!headers.isEmpty()) {
            request.headers(HeadersResolver.from(headers));
        }

        return HttpExecutor.executor(client, request.build(), responseBodyHandler);
    }
}
