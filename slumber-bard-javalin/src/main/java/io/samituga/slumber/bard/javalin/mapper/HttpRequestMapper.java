package io.samituga.slumber.bard.javalin.mapper;

import static io.samituga.bard.endpoint.request.HttpRequestBuilder.httpRequestBuilder;

import io.javalin.http.Context;
import io.samituga.bard.endpoint.request.HttpRequest;
import io.samituga.bard.endpoint.request.type.PathParamName;
import io.samituga.bard.endpoint.request.type.PathParamValue;
import io.samituga.bard.endpoint.request.type.PathParams;
import io.samituga.bard.endpoint.request.type.QueryParamName;
import io.samituga.bard.endpoint.request.type.QueryParamValue;
import io.samituga.bard.endpoint.request.type.QueryParams;
import io.samituga.bard.endpoint.request.type.RequestBody;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class HttpRequestMapper {

    public static HttpRequest fromJavalinContext(Context ctx) {

        var pathParams = getPathParams(ctx);
        var queryParams = getQueryParams(ctx);
        var requestBody = getRequestBody(ctx);

        return httpRequestBuilder()
              .pathParams(PathParams.of(pathParams))
              .queryParams(QueryParams.of(queryParams))
              .request(ctx.req())
              .requestBody(requestBody)
              .build();
    }

    private static Map<PathParamName, PathParamValue> getPathParams(Context ctx) {
        return ctx.pathParamMap().entrySet().stream()
              .map(entry -> Map.entry(
                    PathParamName.of(entry.getKey()),
                    PathParamValue.of(entry.getValue())))
              .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static Map<QueryParamName, Set<QueryParamValue>> getQueryParams(Context ctx) {
        return ctx.queryParamMap().entrySet().stream()
              .map(entry -> Map.entry(
                    QueryParamName.of(entry.getKey()),
                    entry.getValue().stream().map(QueryParamValue::of).collect(Collectors.toSet())
              ))
              .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static Optional<RequestBody> getRequestBody(Context ctx) {
        byte[] bytes = ctx.bodyAsBytes();

        if (bytes == null || bytes.length == 0) {
            return Optional.empty();
        }
        return Optional.of(RequestBody.of(bytes));
    }
}
