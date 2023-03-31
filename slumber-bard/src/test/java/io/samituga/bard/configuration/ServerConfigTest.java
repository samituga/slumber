package io.samituga.bard.configuration;

import static io.samituga.bard.configuration.ServerConfigBuilder.serverConfigBuilder;
import static io.samituga.bard.fixture.FilterTestData.aFilter;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.samituga.bard.filter.Precedence;
import io.samituga.bard.filter.type.Order;
import io.samituga.bard.fixture.ServerConfigTestData;
import io.samituga.bard.type.Port;
import io.samituga.slumber.heimer.validator.exception.AssertionException;
import org.junit.jupiter.api.Test;

class ServerConfigTest {

    private final static Port PORT = Port.of(8080);

    @Test
    void should_make_exact_copy() {
        // given
        var serverConfig = ServerConfigTestData.aServerConfig().build();

        // when
        var copy = serverConfig.copy().build();

        // then
        assertThat(copy).isEqualTo(serverConfig);
    }

    @Test
    void should_throw_exception_if_multiple_first_precedence_filters() {
        // given
        var filter1 = aFilter().order(Order.of(Precedence.FIRST)).build();
        var filter2 = aFilter().order(Order.of(Precedence.FIRST)).build();

        var builder = serverConfigBuilder()
              .port(PORT)
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
              .port(PORT)
              .filters(filter1, filter2);

        // when then
        assertThatThrownBy(builder::build)
              .isInstanceOf(AssertionException.class);
    }
}
