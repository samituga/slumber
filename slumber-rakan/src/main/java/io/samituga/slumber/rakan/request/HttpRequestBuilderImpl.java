package io.samituga.slumber.rakan.request;

import static io.samituga.slumber.heimer.validator.AssertionUtility.required;

import io.samituga.slumber.ivern.http.type.Headers;
import io.samituga.slumber.rakan.executor.HttpExecutor;
import io.samituga.slumber.rakan.helper.HeadersResolver;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class HttpRequestBuilderImpl<T>
      implements
      HttpRequestBuilder.Client<T>,
      HttpRequestBuilder.Uri<T>,
      HttpRequestBuilder.ResponseBodyHandler<T>,
      HttpRequestBuilder.OptionalParameter<T>,
      HttpRequestBuilder.Finalizer<T> {

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);
    public static final HttpClient DEFAULT_HTTP_CLIENT = HttpClient.newBuilder()
          .followRedirects(HttpClient.Redirect.NORMAL)
          .connectTimeout(DEFAULT_TIMEOUT)
          .build();


    private HttpClient httpClient;
    private URI uri;
    private HttpResponse.BodyHandler<T> responseBodyHandler;
    private Headers headers = Headers.empty();
    private Duration timeout = DEFAULT_TIMEOUT;
    private HttpRequest.Builder requestBuilder;

    @Override
    public HttpRequestBuilder.Uri<T> client(HttpClient httpClient) {
        this.httpClient = httpClient;
        return this;
    }

    @Override
    public HttpRequestBuilder.ResponseBodyHandler<T> uri(URI uri) {
        this.uri = uri;
        return this;
    }

    @Override
    public HttpRequestBuilder.OptionalParameter<T> responseBodyHandler(
          HttpResponse.BodyHandler<T> responseBodyHandler) {
        this.responseBodyHandler = responseBodyHandler;
        return this;
    }

    @Override
    public HttpRequestBuilder.OptionalParameter<T> headers(Headers headers) {
        this.headers = headers;
        return this;
    }

    @Override
    public HttpRequestBuilder.OptionalParameter<T> timeout(Duration timeout) {
        this.timeout = timeout;
        return this;
    }

    @Override
    public HttpRequestBuilder.Finalizer<T> httpGet() {
        this.requestBuilder = HttpRequest.newBuilder()
              .GET();
        return this;
    }

    @Override
    public HttpRequestBuilder.Finalizer<T> httpPost(byte[] body) {
        this.requestBuilder = HttpRequest.newBuilder()
              .POST(HttpRequest.BodyPublishers.ofByteArray(required("body", body)));
        return this;
    }

    @Override
    public HttpRequestBuilder.Finalizer<T> httpPut(byte[] body) {
        this.requestBuilder = HttpRequest.newBuilder()
              .PUT(HttpRequest.BodyPublishers.ofByteArray(required("body", body)));
        return this;
    }

    @Override
    public HttpRequestBuilder.Finalizer<T> httpDelete() {
        this.requestBuilder = HttpRequest.newBuilder()
              .DELETE();
        return this;
    }


    @Override
    public HttpResponse<T> execute() throws IOException, InterruptedException {
        validate();

        return HttpExecutor.executor(httpClient, request(requestBuilder), responseBodyHandler)
              .execute();
    }

    @Override
    public CompletableFuture<HttpResponse<T>> executeAsync() {
        validate();

        return HttpExecutor.executor(httpClient, request(requestBuilder), responseBodyHandler)
              .executeAsync();
    }

    private HttpRequest request(HttpRequest.Builder requestBuilder) {
        requestBuilder.uri(uri).timeout(timeout);

        if (!headers.isEmpty()) {
            requestBuilder.headers(HeadersResolver.from(headers));
        }
        return requestBuilder.build();
    }

    public void validate() {
        required("httpClient", httpClient);
        required("uri", uri);
        required("responseBodyHandler", responseBodyHandler);
        required("headers", headers);
        required("timeout", timeout);
    }
}
