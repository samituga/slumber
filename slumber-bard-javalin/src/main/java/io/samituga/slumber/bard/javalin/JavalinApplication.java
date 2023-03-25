package io.samituga.slumber.bard.javalin;

import static io.samituga.bard.ServerStatus.STARTED;
import static io.samituga.bard.ServerStatus.STARTING;
import static io.samituga.bard.ServerStatus.STOPPED;
import static io.samituga.bard.ServerStatus.STOPPING;

import io.javalin.Javalin;
import io.javalin.event.EventListener;
import io.samituga.bard.ServerStatus;
import io.samituga.bard.application.SlumberApplication;
import io.samituga.bard.configuration.ServerConfig;
import io.samituga.bard.exception.ServerInitException;
import io.samituga.bard.exception.ServerShutdownException;
import io.samituga.slumber.bard.javalin.configurator.JavalinConfigurator;
import java.util.function.Consumer;

public class JavalinApplication implements SlumberApplication {

    private final Javalin javalin;
    private ServerStatus serverStatus = STOPPED;


    public JavalinApplication() {
        this.javalin = Javalin.create();
        javalin.events(eventListener());
    }

    public JavalinApplication(Javalin javalin) {
        this.javalin = javalin;
        this.javalin.events(eventListener());
    }

    @Override
    public void init(ServerConfig config) {
        if (serverStatus == STARTED || serverStatus == STARTING) {
            throw new ServerInitException();
        }

        JavalinConfigurator.configure(javalin, config);

        javalin.start(config.port());
    }

    @Override
    public void shutdown() {
        if (serverStatus == STOPPED || serverStatus == STOPPING) {
            throw new ServerShutdownException();
        }
        javalin.close();
    }

    @Override
    public ServerStatus serverStatus() {
        return serverStatus;
    }

    private Consumer<EventListener> eventListener() {
        return eventListener -> {
            eventListener.serverStarting(() -> serverStatus = STARTING);
            eventListener.serverStarted(() -> serverStatus = STARTED);
            eventListener.serverStopping(() -> serverStatus = STOPPING);
            eventListener.serverStopped(() -> serverStatus = STOPPED);
        };
    }
}
