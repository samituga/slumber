package io.samituga.bard.endpoint.response;

import static io.samituga.slumber.heimer.validator.AssertionUtility.required;

import io.samituga.slumber.heimer.util.IoUtils;
import io.samituga.slumber.ivern.http.type.Headers;
import jakarta.servlet.http.HttpServletResponse;

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
    public HttpResponse responseBody(ResponseBody responseBody) {
        try {
            IoUtils.copyTo(responseBody.toInputStream(), response.getOutputStream());
            response.setHeader("Content-Type", responseBody.contentType().value());
            return this;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public HttpServletResponse response() {
        return response;
    }
}
