package io.samituga.bard.endpoint.response;

import static org.assertj.core.api.Assertions.assertThat;

import io.samituga.bard.fixture.HttpResponseTestData;
import org.junit.jupiter.api.Test;

class HttpResponseTest {

    @Test
    void should_make_exact_copy() {
        // given
        var response = HttpResponseTestData.aResponse().build();

        // when
        var copy = response.copy().build();

        // then
        assertThat(copy).isEqualTo(response);
    }
}
