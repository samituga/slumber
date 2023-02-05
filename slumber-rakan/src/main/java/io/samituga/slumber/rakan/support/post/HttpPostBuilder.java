package io.samituga.slumber.rakan.support.post;

import io.samituga.slumber.heimer.validator.AssertionUtility;
import io.samituga.slumber.ivern.http.type.Headers;
import io.samituga.slumber.rakan.helper.HeadersResolver;
import io.samituga.slumber.rakan.support.HttpExecutor;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class HttpPostBuilder {

    static class ClientImpl<T> implements HttpPostPredicate.Client<T> {
        @Override
        public HttpPostPredicate.Uri<T> client(HttpClient client) {
            return new UriImpl<>(client);
        }
    }

    private record UriImpl<T>(HttpClient client) implements HttpPostPredicate.Uri<T> {

        @Override
            public HttpPostPredicate.Body<T> uri(URI uri) {
                return new BodyImpl<>(client, uri);
            }
        }

    private record BodyImpl<T>(HttpClient client, URI uri) implements HttpPostPredicate.Body<T> {


        @Override
            public HttpPostPredicate.ResponseBodyHandler<T> body(byte[] body) {
                return new ResponseBodyHandlerImpl<>(client, uri, body);
            }
        }

    private record ResponseBodyHandlerImpl<T>(HttpClient client, URI uri, byte[] body)
          implements HttpPostPredicate.ResponseBodyHandler<T> {


        @Override
            public HttpPostPredicate.Optional<T> responseBodyHandler(
                  HttpResponse.BodyHandler<T> responseBodyHandler) {
                return new OptionalImpl<>(client, uri, body, responseBodyHandler);
            }
        }

    private static class OptionalImpl<T> implements HttpPostPredicate.Optional<T> {

        private final HttpClient client;
        private final URI uri;
        private final byte[] body;
        private final HttpResponse.BodyHandler<T> responseBodyHandler;

        private Headers headers = Headers.empty();
        private Duration timeout = Duration.ofSeconds(10);

        OptionalImpl(HttpClient client, URI uri, byte[] body,
                     HttpResponse.BodyHandler<T> responseBodyHandler) {
            this.client = client;
            this.uri = uri;
            this.body = body;
            this.responseBodyHandler = responseBodyHandler;
        }


        @Override
        public HttpPostPredicate.Optional<T> headers(Headers headers) {
            this.headers = headers;
            return this;
        }

        @Override
        public HttpPostPredicate.Optional<T> timeout(Duration timeout) {
            this.timeout = timeout;
            return this;
        }


        @Override
        public HttpResponse<T> execute() throws IOException, InterruptedException {
            validate();

            return HttpExecutor.executor(client, request(), responseBodyHandler).execute();
        }

        @Override
        public CompletableFuture<HttpResponse<T>> executeAsync() {
            validate();

            return HttpExecutor.executor(client, request(), responseBodyHandler).executeAsync();
        }

        private HttpRequest request() {
            var request = HttpRequest.newBuilder()
                  .POST(HttpRequest.BodyPublishers.ofByteArray(body))
                  .uri(uri)
                  .timeout(timeout);

            if (!headers.isEmpty()) {
                request.headers(HeadersResolver.from(headers));
            }
            return request.build();
        }

        public void validate() {
            AssertionUtility.required("client", client);
            AssertionUtility.required("uri", uri);
            AssertionUtility.required("body", body);
            AssertionUtility.required("responseBodyHandler", responseBodyHandler);
            AssertionUtility.required("headers", headers);
            AssertionUtility.required("timeout", timeout);
        }
    }
}
