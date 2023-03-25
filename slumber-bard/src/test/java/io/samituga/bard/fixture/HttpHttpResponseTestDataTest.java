package io.samituga.bard.fixture;

import static io.samituga.bard.endpoint.response.HttpResponseBuilder.httpResponseBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.samituga.bard.endpoint.response.HttpCode;
import io.samituga.bard.endpoint.response.ResponseBody;
import io.samituga.bard.endpoint.response.type.ByteResponseBody;
import io.samituga.slumber.heimer.validator.exception.AssertionException;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class HttpHttpResponseTestDataTest {

    private static final ResponseBody RESPONSE_BODY = ByteResponseBody.of("Hello World");

    @Test
    void should_build_response() {
        // given when
        var result = httpResponseBuilder()
              .statusCode(HttpCode.OK)
              .responseBody(Optional.of(RESPONSE_BODY))
              .build();

        // then
        assertThat(result).isNotNull();
        assertThat(result.statusCode()).isEqualTo(HttpCode.OK);
        assertThat(result.responseBody()).isPresent().get().isEqualTo(RESPONSE_BODY);
    }

    @Test
    void should_throw_exception_when_status_code_is_null() {
        // given
        var builder = httpResponseBuilder()
              .statusCode(null)
              .responseBody(Optional.of(RESPONSE_BODY));

        // when then
        assertThatThrownBy(builder::build)
              .isInstanceOf(AssertionException.class);
    }
}
