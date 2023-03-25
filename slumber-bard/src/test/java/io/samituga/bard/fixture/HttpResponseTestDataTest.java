package io.samituga.bard.fixture;

import static io.samituga.bard.fixture.ResponseTestData.responseBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.samituga.bard.endpoint.HttpCode;
import io.samituga.bard.endpoint.ResponseBody;
import io.samituga.bard.endpoint.type.ByteResponseBody;
import io.samituga.slumber.heimer.validator.exception.AssertionException;
import org.junit.jupiter.api.Test;

class HttpResponseTestDataTest {

    private static final ResponseBody RESPONSE_BODY = ByteResponseBody.of("Hello World");

    @Test
    void should_build_response() {
        // given when
        var result = responseBuilder()
              .statusCode(HttpCode.OK)
              .responseBody(RESPONSE_BODY)
              .build();

        // then
        assertThat(result).isNotNull();
        assertThat(result.statusCode()).isEqualTo(HttpCode.OK);
        assertThat(result.responseBody()).isPresent().get().isEqualTo(RESPONSE_BODY);
    }

    @Test
    void should_build_response_with_invalid_data_if_skip_validation_is_true() {
        // given when
        var result = responseBuilder()
              .statusCode(null)
              .responseBody(RESPONSE_BODY)
              .build(true);

        // then
        assertThat(result).isNotNull();
        assertThat(result.statusCode()).isNull();
        assertThat(result.responseBody()).isPresent().get().isEqualTo(RESPONSE_BODY);
    }

    @Test
    void should_throw_exception_when_status_code_is_null() {
        // given
        var builder = responseBuilder()
              .statusCode(null)
              .responseBody(RESPONSE_BODY);

        // when then
        assertThatThrownBy(builder::build)
              .isInstanceOf(AssertionException.class);
    }
}
