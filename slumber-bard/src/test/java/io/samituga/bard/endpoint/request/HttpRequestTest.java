package io.samituga.bard.endpoint.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import io.samituga.bard.fixture.HttpRequestTestData;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;

class HttpRequestTest {

    @Test
    void should_make_exact_copy() {
        // given
        var request = HttpRequestTestData.aRequest(mock(HttpServletRequest.class)).build();

        // when
        var copy = request.copy().build();

        // then
        assertThat(copy).isEqualTo(request);
    }
}
