package io.samituga.bard.endpoint.request;

import static io.samituga.slumber.heimer.validator.AssertionUtility.required;

import io.samituga.bard.endpoint.request.type.PathParams;
import io.samituga.bard.endpoint.request.type.QueryParams;
import io.samituga.bard.endpoint.request.type.RequestBody;
import io.samituga.bard.path.PathParser;
import io.samituga.slumber.ivern.http.type.Headers;
import io.samituga.slumber.ivern.io.IoUtils;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HttpRequestImpl implements HttpRequest {

    private final QueryParams queryParams;
    private final HttpServletRequest request;
    private final PathParser pathParser;

    public HttpRequestImpl(QueryParams queryParams,
                           HttpServletRequest request,
                           String rawPath) {
        this.queryParams = required("queryParams", queryParams);
        this.request = required("request", request);
        this.pathParser = new PathParser(rawPath);
    }

    @Override
    public PathParams pathParams() {
        return PathParams.ofString(pathParser.extractPathParams(request.getPathInfo()));
    }

    @Override
    public QueryParams queryParams() {
        return queryParams;
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
