package io.samituga.slumber.rakan.support.post;

import io.samituga.slumber.ivern.http.type.Headers;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class Abc {
    public static HttpPostPredicate.Client httpPost() {
        return new HttpPostBuilder.ClientImpl<>();
    }

    public interface Client {
        HttpPostPredicate.Uri client(HttpClient client);
    }

    public interface Uri {
        HttpPostPredicate.Body uri(URI uri);
    }

    public interface Body {
        HttpPostPredicate.ResponseBodyHandler<?> body(byte[] body);
    }

    public interface ResponseBodyHandler<T> {
        HttpPostPredicate.Optional<T> responseBodyHandler(HttpResponse.BodyHandler<T> responseBodyHandler);
    }

    public interface Optional<T> {
        HttpPostPredicate.Optional<T> headers(Headers headers);

        HttpPostPredicate.Optional<T> timeout(Duration timeout);

        HttpResponse<T> execute() throws IOException, InterruptedException;
        CompletableFuture<HttpResponse<T>> executeAsync();
    }
}
