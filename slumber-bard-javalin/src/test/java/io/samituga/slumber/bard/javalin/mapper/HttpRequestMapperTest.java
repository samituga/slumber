package io.samituga.slumber.bard.javalin.mapper;

import static io.samituga.bard.endpoint.request.HttpRequestBuilder.httpRequestBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import io.javalin.http.Context;
import io.samituga.bard.endpoint.request.type.PathParams;
import io.samituga.bard.endpoint.request.type.QueryParams;
import io.samituga.slumber.ivern.http.type.Headers;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class HttpRequestMapperTest {

    @Test
    void should_map_correctly() throws IOException {
        // given
        var httpServletRequest = mock(HttpServletRequest.class);
        var ctx = mock(Context.class);

        var headers = Map.of("key1", "value1", "key2", "value2");

        var pathParam1 = Map.entry("one-path", "one-path");
        var pathParam2 = Map.entry("two-path", "two-path");
        var pathParamMap = Map.ofEntries(pathParam1, pathParam2);

        var queryParam1 = Map.entry("one-query", List.of("one-query"));
        var queryParam2 = Map.entry("two-query", List.of("two-query"));
        var queryParamMap = Map.ofEntries(queryParam1, queryParam2);

        var body = "Hello World";

        var mockInputStream = new DelegatingServletInputStream(
              new ByteArrayInputStream(body.getBytes()));
        given(httpServletRequest.getInputStream()).willReturn(mockInputStream);

        given(ctx.req()).willReturn(httpServletRequest);
        given(ctx.headerMap()).willReturn(headers);
        given(ctx.pathParamMap()).willReturn(pathParamMap);
        given(ctx.queryParamMap()).willReturn(queryParamMap);

        // when
        var result = HttpRequestMapper.fromJavalinContext(ctx);

        // then
        var expected = httpRequestBuilder()
              .headers(Headers.of(headers))
              .pathParams(PathParams.ofString(pathParamMap))
              .queryParams(QueryParams.ofString(queryParamMap))
              .request(httpServletRequest)
              .build();
        var requestBody = result.requestBody();
        assertThat(result).isEqualTo(expected);
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
