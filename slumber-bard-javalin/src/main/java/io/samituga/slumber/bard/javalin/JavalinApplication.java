package io.samituga.slumber.bard.javalin;

import io.javalin.Javalin;
import io.samituga.bard.application.SlumberApplication;
import io.samituga.bard.configuration.ServerConfig;
import io.samituga.bard.endpoint.Route;
import io.samituga.bard.exception.ServerInitException;
import io.samituga.bard.exception.ServerShutdownException;
import io.samituga.bard.filter.Filter;

import java.util.Collection;

public class JavalinApplication implements SlumberApplication {

    private boolean isOnline;

    @Override
    public void init(ServerConfig config) {
        if (isOnline) {
            throw new ServerInitException();
        }

        try (var javalin = Javalin.create()) {

            javalin.start(config.port());
            isOnline = true;
            while (true) {

            }
        }

    }

    @Override
    public void shutdown() {
        if (!isOnline) {
            throw new ServerShutdownException();
        }
        isOnline = false;
    }

    private Javalin createJavalin(ServerConfig config) {
        var javalin = Javalin.create();
        addFilters(javalin, config.filters());
        addRoutes(javalin, config.routes());
        return javalin;
    }


    private void addRoutes(Javalin javalin, Collection<Route<?>> routes) {
        for (Route<?> route : routes) {

        }
    }

    private void addRouteForMethod(Javalin javalin, Route<?> route) {
//        javalin.addHandler()
    }
}
