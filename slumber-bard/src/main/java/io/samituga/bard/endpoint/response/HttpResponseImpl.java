package io.samituga.bard.endpoint.response;

import static io.samituga.slumber.heimer.validator.AssertionUtility.required;

import io.samituga.bard.endpoint.response.type.ByteResponseBody;
import io.samituga.bard.endpoint.response.type.InputStreamResponseBody;
import io.samituga.slumber.heimer.util.IoUtils;
import io.samituga.slumber.ivern.http.type.Headers;
import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;

public class HttpResponseImpl implements HttpResponse {

    private final HttpServletResponse response;

    public HttpResponseImpl(HttpServletResponse response) {
        this.response = required("response", response);
    }

    @Override
    public HttpResponse statusCode(HttpCode statusCode) {
        response.setStatus(statusCode.code());
        return this;
    }

    @Override
    public HttpResponse headers(Headers headers) {
        headers.value().forEach(response::setHeader);
        return this;
    }

    @Override
    public HttpResponse responseBody(ByteResponseBody responseBody) {
        var byteArrayInputStream = new ByteArrayInputStream(responseBody.responseBody());
        return responseBody(new InputStreamResponseBody(byteArrayInputStream));
    }

    @Override
    public HttpResponse responseBody(InputStreamResponseBody responseBody) {
        try {
            IoUtils.copyTo(responseBody.responseBody(), response.getOutputStream());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return this;
    }

    @Override
    public HttpServletResponse response() {
        return response;
    }
}
