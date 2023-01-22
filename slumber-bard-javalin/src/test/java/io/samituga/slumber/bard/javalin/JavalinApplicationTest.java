package io.samituga.slumber.bard.javalin;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

import io.javalin.Javalin;
import io.samituga.bard.exception.ServerInitException;
import io.samituga.bard.exception.ServerShutdownException;
import io.samituga.bard.fixture.ServerConfigTestData;
import org.junit.jupiter.api.Test;

class JavalinApplicationTest {

    private final Javalin javalin = mock(Javalin.class);


    @Test
    void should_throw_exception_when_server_is_already_initialized() {
        var javalinApplication = new JavalinApplication(javalin);
        var serverConfig = ServerConfigTestData.defaultServerConfig().build();

        javalinApplication.init(serverConfig);

        assertThatThrownBy(() -> javalinApplication.init(serverConfig))
              .isInstanceOf(ServerInitException.class);
    }

    @Test
    void should_throw_exception_when_server_is_already_stopped() {
        var javalinApplication = new JavalinApplication(javalin);
        var serverConfig = ServerConfigTestData.defaultServerConfig().build();

        javalinApplication.init(serverConfig);
        javalinApplication.shutdown();

        assertThatThrownBy(javalinApplication::shutdown)
              .isInstanceOf(ServerShutdownException.class);
    }
}