package io.samituga.bard.fixture;

import static io.samituga.bard.configuration.ServerConfigBuilder.serverConfigBuilder;
import static io.samituga.bard.fixture.FilterTestData.aFilter;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.samituga.bard.filter.Filter;
import io.samituga.bard.filter.Precedence;
import io.samituga.bard.filter.type.Order;
import io.samituga.slumber.heimer.validator.exception.AssertionException;
import org.junit.jupiter.api.Test;

class ServerConfigTestDataTest {

    private final static int VALID_PORT = 8080;
    private final static Filter FILTER = aFilter().build();


    @Test
    void should_build_server_config() {
        // given when
        var result = serverConfigBuilder()
              .port(VALID_PORT)
              .filter(FILTER)
              .build();

        // then
        assertThat(result).isNotNull();
        assertThat(result.port()).isEqualTo(VALID_PORT);
        assertThat(result.filters()).containsExactly(FILTER);
    }

    @Test
    void should_throw_exception_if_port_is_invalid() {
        // given
        var invalidPort = -1;

        var builder = serverConfigBuilder()
              .port(invalidPort)
              .filter(FILTER);

        // when then
        assertThatThrownBy(builder::build)
              .isInstanceOf(AssertionException.class);
    }

    @Test
    void should_throw_exception_if_multiple_first_precedence_filters() {
        // given
        var filter1 = aFilter().order(Order.of(Precedence.FIRST)).build();
        var filter2 = aFilter().order(Order.of(Precedence.FIRST)).build();

        var builder = serverConfigBuilder()
              .port(VALID_PORT)
              .filters(filter1, filter2);

        // when then
        assertThatThrownBy(builder::build)
              .isInstanceOf(AssertionException.class);
    }

    @Test
    void should_throw_exception_if_multiple_last_precedence_filters() {
        // given
        var filter1 = aFilter().order(Order.of(Precedence.LAST)).build();
        var filter2 = aFilter().order(Order.of(Precedence.LAST)).build();

        var builder = serverConfigBuilder()
              .port(VALID_PORT)
              .filters(filter1, filter2);

        // when then
        assertThatThrownBy(builder::build)
              .isInstanceOf(AssertionException.class);
    }
}
