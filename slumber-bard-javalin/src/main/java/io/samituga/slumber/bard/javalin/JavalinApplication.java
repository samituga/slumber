package io.samituga.slumber.bard.javalin;

import static io.samituga.slumber.bard.javalin.JavalinConfigurator.addFilters;
import static io.samituga.slumber.bard.javalin.JavalinConfigurator.addRoutes;

import io.javalin.Javalin;
import io.samituga.bard.application.SlumberApplication;
import io.samituga.bard.configuration.ServerConfig;
import io.samituga.bard.exception.ServerInitException;
import io.samituga.bard.exception.ServerShutdownException;

public class JavalinApplication implements SlumberApplication {

    private boolean isOnline;
    private final Javalin javalin;


    public JavalinApplication() {
        this(Javalin.create());
    }

    public JavalinApplication(Javalin javalin) {
        this.javalin = javalin;
    }

    @Override
    public void init(ServerConfig config) {
        if (isOnline) {
            throw new ServerInitException();
        }

        javalin.start(config.port());
        isOnline = true;
    }

    @Override
    public void shutdown() {
        if (!isOnline) {
            throw new ServerShutdownException();
        }
        javalin.close();
        isOnline = false;
    }

    private Javalin createJavalin(ServerConfig config) {
        var javalin = Javalin.create();
        addFilters(javalin, config.filters());
        addRoutes(javalin, config.routes());
        return javalin;
    }
}
