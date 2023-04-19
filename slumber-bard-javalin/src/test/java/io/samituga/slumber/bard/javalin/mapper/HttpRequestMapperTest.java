package io.samituga.slumber.bard.javalin.mapper;

import static java.util.Map.entry;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import io.javalin.http.Context;
import io.samituga.bard.endpoint.request.type.PathParams;
import io.samituga.bard.endpoint.request.type.QueryParams;
import io.samituga.slumber.heimer.util.UrlQueryUtils;
import io.samituga.slumber.ivern.http.type.Headers;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

class HttpRequestMapperTest { // TODO: 18/04/2023 Move this to HttpRequestTest class

    @Test
    void should_map_correctly() throws IOException {
        // given
        var httpServletRequest = mock(HttpServletRequest.class);
        var ctx = mock(Context.class);

        var header1 = entry("key1", "value1");
        var header2 = entry("key2", "value2");
        var headers = Map.ofEntries(header1, header2);

        var pathParam1 = entry("one-path", "one-path");
        var pathParam2 = entry("two-path", "two-path");
        var pathParamMap = Map.ofEntries(pathParam1, pathParam2);

        var queryParam1 = entry("one-query", List.of("one-query"));
        var queryParam2 = entry("two-query", List.of("two-query"));
        var queryParamMap = Map.ofEntries(queryParam1, queryParam2);

        var body = "Hello World";

        var mockInputStream = new DelegatingServletInputStream(
              new ByteArrayInputStream(body.getBytes()));

        given(httpServletRequest.getInputStream()).willReturn(mockInputStream);
        given(httpServletRequest.getHeaderNames())
              .willReturn(Collections.enumeration(headers.keySet()));
        given(httpServletRequest.getPathInfo())
              .willReturn("/" + pathParam1.getValue() + "/" + pathParam2.getValue());
        given(httpServletRequest.getQueryString())
              .willReturn(UrlQueryUtils.createQueryString(queryParamMap));
        headers.forEach((k, v) -> given(httpServletRequest.getHeader(k)).willReturn(v));

        given(ctx.req()).willReturn(httpServletRequest);
        given(ctx.matchedPath()).willReturn(
              "/{" + pathParam1.getKey() + "}/{" + pathParam2.getKey() + "}");

        // when
        var result = HttpRequestMapper.fromJavalinContext(ctx);

        // then
        var requestBody = result.requestBody();
        assertThat(result.headers()).isEqualTo(Headers.of(headers));
        assertThat(result.pathParams()).isEqualTo(PathParams.ofString(pathParamMap));
        assertThat(result.queryParams()).isEqualTo(QueryParams.ofString(queryParamMap));
        // TODO: 15/04/2023 Should be able to compare Type directly
        assertThat(requestBody.orElseThrow().value()).isEqualTo(body.getBytes());
    }

    private static class DelegatingServletInputStream extends ServletInputStream {
        private final InputStream delegate;

        public DelegatingServletInputStream(InputStream delegate) {
            this.delegate = delegate;
        }

        @Override
        public int read() throws IOException {
            return delegate.read();
        }

        @Override
        public void close() throws IOException {
            delegate.close();
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setReadListener(ReadListener readListener) {

        }
    }
}
