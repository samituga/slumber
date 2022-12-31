package io.samituga.bard.configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.samituga.slumber.heimer.exception.AssertionException;
import org.junit.jupiter.api.Test;

class ServerConfigTest {
    @Test
    void should_create_instance_when_arguments_are_valid() {
        var port = 50;

        var serverConfig = ServerConfig.builder()
              .port(port)
              .build();

        assertThat(serverConfig.port()).isEqualTo(port);
    }

    @Test
    void should_fail_validation_when_mandatory_fields_are_invalid() {
        var invalidPort = -1;

        assertThatThrownBy(
              () -> ServerConfig.builder()
                    .port(invalidPort)
                    .build())
              .isInstanceOf(AssertionException.class);
    }

    @Test
    void should_create_exact_copy() {
        var port = 50;

        var serverConfig = ServerConfig.builder()
              .port(port)
              .build();

        var copy = ServerConfig.builder().copy(serverConfig).build();

        assertThat(copy).isEqualTo(serverConfig);
    }
}