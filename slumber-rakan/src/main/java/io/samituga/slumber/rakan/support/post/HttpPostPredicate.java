package io.samituga.slumber.rakan.support.post;

import io.samituga.slumber.ivern.http.type.Headers;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class HttpPostPredicate {

    public static <T> Client<T> httpPost() {
        return new HttpPostBuilder.ClientImpl<>();
    }

    public interface Client<T> {
        Uri<T> client(HttpClient client);
    }

    public interface Uri<T> {
        Body<T> uri(URI uri);
    }

    public interface Body<T> {
        ResponseBodyHandler<T> body(byte[] body);
    }

    public interface ResponseBodyHandler<T> {
        Optional<T> responseBodyHandler(HttpResponse.BodyHandler<T> responseBodyHandler);
    }

    public interface Optional<T> {
        Optional<T> headers(Headers headers);

        Optional<T> timeout(Duration timeout);

        HttpResponse<T> execute() throws IOException, InterruptedException;
        CompletableFuture<HttpResponse<T>> executeAsync();
    }
}
