package io.samituga.slumber.rakan.request;

import io.samituga.slumber.ivern.http.type.Headers;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public interface HttpRequestBuilder {

    static <T> Client<T> request(Class<T> responseBodyType) {
        return new HttpRequestBuilderImpl<>();
    }

    interface Client<T> {
        Uri<T> client(HttpClient client);
    }

    interface Uri<T> {
        ResponseBodyHandler<T> uri(URI uri);
    }

    interface ResponseBodyHandler<T> {
        OptionalParameter<T> responseBodyHandler(HttpResponse.BodyHandler<T> responseBodyHandler);
    }

    interface OptionalParameter<T> {
        OptionalParameter<T> headers(Headers headers);

        OptionalParameter<T> timeout(Duration timeout);

        Finalizer<T> httpGet();

        Finalizer<T> httpPost(byte[] body);

        Finalizer<T> httpPut(byte[] body);

        Finalizer<T> httpDelete();
    }

    interface Finalizer<T> {
        HttpResponse<T> execute() throws IOException, InterruptedException;

        CompletableFuture<HttpResponse<T>> executeAsync();
    }
}
