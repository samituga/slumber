package io.samituga.slumber.bard.javalin;

import static io.samituga.bard.application.ServerStatus.STARTED;
import static io.samituga.bard.application.ServerStatus.STARTING;
import static io.samituga.bard.application.ServerStatus.STOPPED;
import static io.samituga.bard.application.ServerStatus.STOPPING;

import io.javalin.Javalin;
import io.javalin.event.EventListener;
import io.samituga.bard.application.ServerStatus;
import io.samituga.bard.application.SlumberApplication;
import io.samituga.bard.configuration.ServerConfig;
import io.samituga.bard.exception.ServerInitException;
import io.samituga.bard.exception.ServerShutdownException;
import io.samituga.jayce.Json;
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

        Json.initWithModules(config.jacksonModules());

        JavalinConfigurator.configure(javalin, config);

        javalin.start(config.port().value());
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
