package io.samituga.bard.fixture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.samituga.bard.endpoint.Request;
import io.samituga.bard.endpoint.Response;
import io.samituga.bard.endpoint.Verb;
import io.samituga.bard.endpoint.type.Path;
import io.samituga.slumber.heimer.validator.exception.AssertionException;
import java.util.function.Function;
import org.junit.jupiter.api.Test;

class RouteTestDataTest {

    private static final Verb VERB = Verb.GET;
    private static final Path PATH = Path.of("/hello");
    private static final Function<Request, Response<String>> HANDLER =
          req -> ResponseTestData.<String>defaultResponse().build();

    @Test
    void should_build_route() {
        var result = RouteTestData.<String>routeBuilder()
              .verb(VERB)
              .path(PATH)
              .handler(HANDLER)
              .build();

        assertThat(result).isNotNull();
        assertThat(result.verb()).isEqualTo(VERB);
        assertThat(result.path()).isEqualTo(PATH);
        assertThat(result.handler()).isEqualTo(HANDLER);
    }

    @Test
    void should_build_response_with_invalid_data_if_skip_validation_is_true() {
        var result = RouteTestData.<String>routeBuilder()
              .verb(null)
              .path(PATH)
              .handler(HANDLER)
              .build(true);

        assertThat(result).isNotNull();
        assertThat(result.verb()).isNull();
        assertThat(result.path()).isEqualTo(PATH);
        assertThat(result.handler()).isEqualTo(HANDLER);
    }

    @Test
    void should_throw_exception_when_verb_is_null() {
        var builder = RouteTestData.<String>routeBuilder()
              .verb(null)
              .path(PATH)
              .handler(HANDLER);

        assertThatThrownBy(builder::build)
              .isInstanceOf(AssertionException.class);
    }

    @Test
    void should_throw_exception_when_path_is_null() {
        var builder = RouteTestData.<String>routeBuilder()
              .verb(VERB)
              .path(null)
              .handler(HANDLER);

        assertThatThrownBy(builder::build)
              .isInstanceOf(AssertionException.class);
    }

    @Test
    void should_throw_exception_when_handler_is_null() {
        var builder = RouteTestData.<String>routeBuilder()
              .verb(VERB)
              .path(PATH)
              .handler(null);

        assertThatThrownBy(builder::build)
              .isInstanceOf(AssertionException.class);
    }
}
