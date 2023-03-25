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
import java.util.Map;
import java.util.stream.Collectors;

public class RequestMapper {

    public static HttpRequest fromContext(Context context) {

        var pathParams = context.pathParamMap().entrySet().stream()
              .map(entry -> Map.entry(
                    PathParamName.of(entry.getKey()),
                    PathParamValue.of(entry.getValue())))
              .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        var queryParams = context.queryParamMap().entrySet().stream()
              .map(entry -> Map.entry(
                    QueryParamName.of(entry.getKey()),
                    entry.getValue().stream().map(QueryParamValue::of).collect(Collectors.toSet())
              ))
              .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return httpRequestBuilder()
              .pathParams(PathParams.of(pathParams))
              .queryParams(QueryParams.of(queryParams))
              .request(context.req())
              .build();
    }
}
