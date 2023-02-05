package io.samituga.slumber.rakan.support;

import static io.samituga.slumber.heimer.validator.AssertionUtility.required;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class HttpExecutor<T> {


    private final HttpClient client;
    private final HttpRequest request;
    private final HttpResponse.BodyHandler<T> responseBodyHandler;

    private HttpExecutor(HttpClient client,
                         HttpRequest request,
                         HttpResponse.BodyHandler<T> responseBodyHandler) {
        this.client = required("client", client);
        this.request = required("request", request);
        this.responseBodyHandler = required("responseBodyHandler", responseBodyHandler);
    }

    public static <T> HttpExecutor<T> executor(HttpClient client,
                                               HttpRequest request,
                                               HttpResponse.BodyHandler<T> responseBodyHandler) {
        return new HttpExecutor<>(client, request, responseBodyHandler);
    }


    public HttpResponse<T> execute() throws IOException, InterruptedException {

        // TODO: 2023-01-28 Logging
        // TODO: 2023-01-28 Handle error?
        return client.send(request, responseBodyHandler);
    }

    public CompletableFuture<HttpResponse<T>> executeAsync() {
        // TODO: 2023-01-28 Logging
        // TODO: 2023-01-28 Handle error?
        return client.sendAsync(request, responseBodyHandler);
    }
}
