package io.samituga.bard.endpoint.request;

import static org.assertj.core.api.Assertions.assertThat;

import io.samituga.bard.fixture.HttpRequestTestData;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class HttpRequestTest {

    @Test
    void should_make_exact_copy() {
        // given
        var request = HttpRequestTestData.aRequest()
              .request(Mockito.mock(HttpServletRequest.class))
              .build();

        // when
        var copy = request.copy().build();

        // then
        assertThat(copy).isEqualTo(request);
    }
}
