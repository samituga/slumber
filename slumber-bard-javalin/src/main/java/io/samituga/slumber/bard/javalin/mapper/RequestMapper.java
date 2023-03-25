package io.samituga.slumber.bard.javalin.mapper;

import io.javalin.http.Context;
import io.samituga.bard.endpoint.HttpRequest;
import io.samituga.bard.endpoint.type.PathParamName;
import io.samituga.bard.endpoint.type.PathParamValue;
import io.samituga.bard.endpoint.type.PathParams;
import io.samituga.bard.endpoint.type.QueryParamName;
import io.samituga.bard.endpoint.type.QueryParamValue;
import io.samituga.bard.endpoint.type.QueryParams;
import jakarta.servlet.http.HttpServletRequest;
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

        return new HttpRequest() {
            @Override
            public PathParams pathParams() {
                return PathParams.of(pathParams);
            }

            @Override
            public QueryParams queryParams() {
                return QueryParams.of(queryParams);
            }

            @Override
            public HttpServletRequest request() {
                return context.req();
            }
        };
    }

}
