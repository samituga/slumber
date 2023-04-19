package io.samituga.bard.endpoint.request;

import static io.samituga.slumber.heimer.util.UrlQueryUtils.parseQueryParams;
import static io.samituga.slumber.heimer.validator.AssertionUtility.required;

import io.samituga.bard.endpoint.request.type.PathParams;
import io.samituga.bard.endpoint.request.type.QueryParams;
import io.samituga.bard.endpoint.request.type.RequestBody;
import io.samituga.bard.path.PathParser;
import io.samituga.slumber.heimer.util.IoUtils;
import io.samituga.slumber.ivern.http.type.Headers;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HttpRequestImpl implements HttpRequest {
    private final HttpServletRequest request;
    private final PathParser pathParser;

    public HttpRequestImpl(HttpServletRequest request,
                           String rawPath) {
        this.request = required("request", request);
        this.pathParser = new PathParser(rawPath);
    }

    @Override
    public PathParams pathParams() {
        var pathParamsMap = pathParser.extractPathParams(request.getPathInfo());
        return PathParams.of(pathParamsMap);
    }

    @Override
    public QueryParams queryParams() {
        return Optional.ofNullable(request.getQueryString())
              .map(query -> parseQueryParams(request.getQueryString()))
              .map(QueryParams::of)
              .orElse(QueryParams.empty());
    }

    @Override
    public HttpServletRequest request() {
        return request;
    }

    @Override
    public Headers headers() {
        Map<String, String> headers = new HashMap<>();

        request.getHeaderNames().asIterator().forEachRemaining(
              headerName -> headers.put(headerName, request.getHeader(headerName)));

        return Headers.of(headers);
    }

    @Override
    public Optional<RequestBody> requestBody() {
        try {
            byte[] bytes = IoUtils.toBytes(request.getInputStream());
            return bytes.length > 0 ? Optional.of(RequestBody.of(bytes)) : Optional.empty();
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read the request body", e);
        }
    }
}
