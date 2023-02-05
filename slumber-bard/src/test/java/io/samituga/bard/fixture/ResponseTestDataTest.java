package io.samituga.bard.fixture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.samituga.bard.endpoint.HttpCode;
import io.samituga.slumber.heimer.validator.exception.AssertionException;
import org.junit.jupiter.api.Test;

class ResponseTestDataTest {

    private static final String RESPONSE_BODY = "Hello World";

    @Test
    void should_build_response() {
        var result = ResponseTestData.<String>responseBuilder()
              .statusCode(HttpCode.OK)
              .responseBody(RESPONSE_BODY)
              .build();

        assertThat(result).isNotNull();
        assertThat(result.statusCode()).isEqualTo(HttpCode.OK);
        assertThat(result.responseBody()).isPresent().get().isEqualTo(RESPONSE_BODY);
    }

    @Test
    void should_build_response_with_invalid_data_if_skip_validation_is_true() {
        var result = ResponseTestData.<String>responseBuilder()
              .statusCode(null)
              .responseBody(RESPONSE_BODY)
              .build(true);

        assertThat(result).isNotNull();
        assertThat(result.statusCode()).isNull();
        assertThat(result.responseBody()).isPresent().get().isEqualTo(RESPONSE_BODY);
    }

    @Test
    void should_throw_exception_when_status_code_is_null() {
        var builder = ResponseTestData.<String>responseBuilder()
              .statusCode(null)
              .responseBody(RESPONSE_BODY);

        assertThatThrownBy(builder::build)
              .isInstanceOf(AssertionException.class);
    }
}