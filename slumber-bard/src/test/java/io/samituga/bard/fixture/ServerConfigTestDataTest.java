package io.samituga.bard.fixture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.samituga.bard.filter.Filter;
import io.samituga.bard.filter.Precedence;
import io.samituga.bard.filter.type.Order;
import io.samituga.slumber.heimer.validator.exception.AssertionException;
import org.junit.jupiter.api.Test;

class ServerConfigTestDataTest {

    private final static int VALID_PORT = 8080;
    private final static Filter FILTER = FilterTestData.defaultFilter().build();


    @Test
    void should_build_server_config() {
        var result = ServerConfigTestData.serverConfigBuilder()
              .port(VALID_PORT)
              .filter(FILTER)
              .build();

        assertThat(result).isNotNull();
        assertThat(result.port()).isEqualTo(VALID_PORT);
        assertThat(result.filters()).containsExactly(FILTER);
    }

    @Test
    void should_build_server_config_with_invalid_data_if_skip_validation_is_true() {
        var invalidPort = -1;

        var result = ServerConfigTestData.serverConfigBuilder()
              .port(invalidPort)
              .filter(FILTER)
              .build(true);

        assertThat(result).isNotNull();
        assertThat(result.port()).isEqualTo(invalidPort);
        assertThat(result.filters()).containsExactly(FILTER);
    }

    @Test
    void should_throw_exception_if_port_is_invalid() {
        var invalidPort = -1;

        var builder = ServerConfigTestData.serverConfigBuilder()
              .port(invalidPort)
              .filter(FILTER);

        assertThatThrownBy(builder::build)
              .isInstanceOf(AssertionException.class);
    }

    @Test
    void should_throw_exception_if_multiple_first_precedence_filters() {
        var filter1 = FilterTestData.defaultFilter().order(Order.of(Precedence.FIRST)).build();
        var filter2 = FilterTestData.defaultFilter().order(Order.of(Precedence.FIRST)).build();

        var builder = ServerConfigTestData.serverConfigBuilder()
              .port(VALID_PORT)
              .filters(filter1, filter2);

        assertThatThrownBy(builder::build)
              .isInstanceOf(AssertionException.class);
    }

    @Test
    void should_throw_exception_if_multiple_last_precedence_filters() {
        var filter1 = FilterTestData.defaultFilter().order(Order.of(Precedence.LAST)).build();
        var filter2 = FilterTestData.defaultFilter().order(Order.of(Precedence.LAST)).build();

        var builder = ServerConfigTestData.serverConfigBuilder()
              .port(VALID_PORT)
              .filters(filter1, filter2);

        assertThatThrownBy(builder::build)
              .isInstanceOf(AssertionException.class);
    }
}
