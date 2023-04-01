package io.samituga.slumber.bard.javalin.mapper;

import static io.samituga.bard.endpoint.request.HttpRequestBuilder.httpRequestBuilder;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import io.javalin.http.Context;
import io.samituga.bard.endpoint.request.type.PathParams;
import io.samituga.bard.endpoint.request.type.QueryParams;
import io.samituga.bard.endpoint.request.type.RequestBody;
import io.samituga.slumber.ivern.http.type.Headers;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class HttpRequestMapperTest {

    @Test
    void should_map_correctly() {
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

        var bodyBytes = "Hello World".getBytes(UTF_8);
        given(ctx.headerMap()).willReturn(headers);
        given(ctx.req()).willReturn(httpServletRequest);
        given(ctx.pathParamMap()).willReturn(pathParamMap);
        given(ctx.queryParamMap()).willReturn(queryParamMap);
        given(ctx.bodyAsBytes()).willReturn(bodyBytes);

        // when
        var result = HttpRequestMapper.fromJavalinContext(ctx);

        // then
        var expected = httpRequestBuilder()
              .headers(Headers.of(headers))
              .pathParams(PathParams.ofString(pathParamMap))
              .queryParams(QueryParams.ofString(queryParamMap))
              .request(httpServletRequest)
              .requestBody(Optional.of(RequestBody.of(bodyBytes)))
              .build();
        assertThat(result).isEqualTo(expected);
    }
}
