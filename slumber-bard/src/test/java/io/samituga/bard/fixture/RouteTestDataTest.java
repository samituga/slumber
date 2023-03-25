package io.samituga.bard.fixture;

import static io.samituga.bard.fixture.ResponseTestData.aResponse;
import static io.samituga.bard.fixture.RouteTestData.routeBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.samituga.bard.endpoint.HttpRequest;
import io.samituga.bard.endpoint.HttpResponse;
import io.samituga.bard.endpoint.Verb;
import io.samituga.bard.endpoint.type.Path;
import io.samituga.slumber.heimer.validator.exception.AssertionException;
import java.util.function.Function;
import org.junit.jupiter.api.Test;

class RouteTestDataTest {

    private static final Verb VERB = Verb.GET;
    private static final Path PATH = Path.of("/hello");
    private static final Function<HttpRequest, HttpResponse> HANDLER =
          req -> aResponse().build();

    @Test
    void should_build_route() {
        // given when
        var result = routeBuilder()
              .verb(VERB)
              .path(PATH)
              .handler(HANDLER)
              .build();

        // then
        assertThat(result).isNotNull();
        assertThat(result.verb()).isEqualTo(VERB);
        assertThat(result.path()).isEqualTo(PATH);
        assertThat(result.handler()).isEqualTo(HANDLER);
    }

    @Test
    void should_build_response_with_invalid_data_if_skip_validation_is_true() {
        // given when
        var result = routeBuilder()
              .verb(null)
              .path(PATH)
              .handler(HANDLER)
              .build(true);

        // then
        assertThat(result).isNotNull();
        assertThat(result.verb()).isNull();
        assertThat(result.path()).isEqualTo(PATH);
        assertThat(result.handler()).isEqualTo(HANDLER);
    }

    @Test
    void should_throw_exception_when_verb_is_null() {
        // given
        var builder = routeBuilder()
              .verb(null)
              .path(PATH)
              .handler(HANDLER);

        // when then
        assertThatThrownBy(builder::build)
              .isInstanceOf(AssertionException.class);
    }

    @Test
    void should_throw_exception_when_path_is_null() {
        // given
        var builder = routeBuilder()
              .verb(VERB)
              .path(null)
              .handler(HANDLER);

        // when then
        assertThatThrownBy(builder::build)
              .isInstanceOf(AssertionException.class);
    }

    @Test
    void should_throw_exception_when_handler_is_null() {
        // given
        var builder = routeBuilder()
              .verb(VERB)
              .path(PATH)
              .handler(null);

        // when then
        assertThatThrownBy(builder::build)
              .isInstanceOf(AssertionException.class);
    }
}
