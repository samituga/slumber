package io.samituga.bard.endpoint.context;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import io.samituga.bard.fixture.HttpContextTestData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

public class HttpContextTest {


    @Test
    void should_make_exact_copy() {
        // given
        var ctx = HttpContextTestData.aHttpContext(
                    mock(HttpServletRequest.class),
                    mock(HttpServletResponse.class))
              .build();

        // when
        var copy = ctx.copy().build();

        // then
        assertThat(copy).isEqualTo(ctx);
    }
}
