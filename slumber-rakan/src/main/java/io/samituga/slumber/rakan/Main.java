package io.samituga.slumber.rakan;

import io.samituga.slumber.rakan.support.HttpClientSupport;
import io.samituga.slumber.rakan.support.post.HttpPostPredicate;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;

public class Main {
    public static void main(String[] args) {
        try {
            var result = HttpPostPredicate.<String>httpPost()
                  .client(HttpClientSupport.DEFAULT_HTTP_CLIENT)
                  .uri(URI.create("https://samitga.free.beeceptor.com"))
                  .body("Hello World".getBytes())
                  .responseBodyHandler(HttpResponse.BodyHandlers.ofString())
                  .execute();

            System.out.println(result.body());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
