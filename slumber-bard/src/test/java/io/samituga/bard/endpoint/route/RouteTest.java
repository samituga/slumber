package io.samituga.bard.endpoint.route;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import io.samituga.bard.fixture.RouteTestData;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

class RouteTest {

      @Test
      void should_make_exact_copy() {
          // given
          var route = RouteTestData.aRoute().build();

          // when
          var copy = route.copy().build();

          // then
          assertThat(copy).isEqualTo(route);
      }
}
