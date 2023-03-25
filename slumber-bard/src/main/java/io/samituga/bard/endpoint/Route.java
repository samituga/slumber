package io.samituga.bard.endpoint;

import io.samituga.bard.endpoint.request.HttpRequest;
import io.samituga.bard.endpoint.response.HttpResponse;
import io.samituga.bard.endpoint.type.Path;
import java.util.function.Function;

public interface Route {

    Verb verb();

    Path path();

    Function<HttpRequest, HttpResponse> handler();
}
