package io.samituga.slumber.bard.javalin.mapper;

import static io.samituga.bard.endpoint.response.HttpResponseBuilder.httpResponseBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import io.javalin.http.Context;
import io.samituga.bard.endpoint.response.HttpCode;
import io.samituga.bard.endpoint.response.type.ByteResponseBody;
import io.samituga.slumber.ivern.http.type.Headers;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class HttpResponseMapperTest {


    @Test
    void should_map_correctly() {
        // given
        var httpServletResponse = Mockito.mock(HttpServletResponse.class);
        var ctx = Mockito.mock(Context.class);

        var statusCode = 200;
        given(ctx.res()).willReturn(httpServletResponse);
        given(ctx.statusCode()).willReturn(statusCode);

        var header1 = Map.entry("one-key", "one-value");
        var header2 = Map.entry("two-key", "two-value");
        var headerNames = List.of(header1.getKey(), header2.getKey());
        given(httpServletResponse.getHeaderNames()).willReturn(headerNames);
        given(httpServletResponse.getHeader(header1.getKey())).willReturn(header1.getValue());
        given(httpServletResponse.getHeader(header2.getKey())).willReturn(header2.getValue());

        var responseBody = ByteResponseBody.of("Hello World");

        // when
        var result = HttpResponseMapper.fromJavalinContext(ctx, Optional.of(responseBody));

        // then
        var expected = httpResponseBuilder()
              .statusCode(HttpCode.fromStatusCode(statusCode))
              .headers(Headers.of(Map.ofEntries(header1, header2)))
              .response(httpServletResponse)
              .responseBody(responseBody)
              .build();
        assertThat(result).isEqualTo(expected);
    }
}
