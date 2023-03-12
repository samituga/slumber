package io.samituga.slumber.bard.javalin;

import static io.samituga.bard.fixture.ServerConfigTestData.aServerConfig;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.javalin.Javalin;
import io.javalin.event.EventListener;
import io.javalin.event.EventManager;
import io.javalin.event.JavalinEvent;
import io.samituga.bard.exception.ServerInitException;
import io.samituga.bard.exception.ServerShutdownException;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;

class JavalinApplicationTest {

    private final Javalin javalin = mock(Javalin.class);


    @Test
    void should_throw_exception_when_server_is_already_initialized() {
        // given
        var eventManager = new EventManager();
        var eventListener = new EventListener(eventManager);

        when(javalin.events(any())).then((invocation) -> {
            Consumer<EventListener> arg = invocation.getArgument(0);
            arg.accept(eventListener);
            return javalin;
        });

        var javalinApplication = new JavalinApplication(javalin);
        var serverConfig = aServerConfig().build();

        javalinApplication.init(serverConfig);
        eventManager.fireEvent(JavalinEvent.SERVER_STARTING);

        // when then
        assertThatThrownBy(() -> javalinApplication.init(serverConfig))
              .isInstanceOf(ServerInitException.class);
    }

    @Test
    void should_throw_exception_when_server_is_already_stopped() {
        // given
        var javalinApplication = new JavalinApplication(javalin);

        // when then
        assertThatThrownBy(javalinApplication::shutdown)
              .isInstanceOf(ServerShutdownException.class);
    }
}
