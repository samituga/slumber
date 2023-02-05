package io.samituga.slumber.bard.javalin;

import io.javalin.Javalin;
import io.javalin.event.EventListener;
import io.samituga.bard.ServerStatus;
import io.samituga.bard.application.SlumberApplication;
import io.samituga.bard.configuration.ServerConfig;
import io.samituga.bard.exception.ServerInitException;
import io.samituga.bard.exception.ServerShutdownException;

import java.util.function.Consumer;

import static io.samituga.bard.ServerStatus.STARTED;
import static io.samituga.bard.ServerStatus.STARTING;
import static io.samituga.bard.ServerStatus.STOPPED;
import static io.samituga.bard.ServerStatus.STOPPING;
import static io.samituga.slumber.bard.javalin.JavalinConfigurator.addFilters;
import static io.samituga.slumber.bard.javalin.JavalinConfigurator.addRoutes;

public class JavalinApplication implements SlumberApplication {

    private final Javalin javalin;
    private ServerStatus serverStatus;


    public JavalinApplication() {
        this(Javalin.create());
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

        configureJavalin(config);

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

    private void configureJavalin(ServerConfig config) {
        addFilters(javalin, config.filters());
        addRoutes(javalin, config.routes());
    }
}
