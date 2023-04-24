package io.samituga.bard.configuration;

import com.fasterxml.jackson.databind.Module;
import io.samituga.bard.endpoint.route.Route;
import io.samituga.bard.filter.Filter;
import io.samituga.bard.handler.ExceptionHandler;
import io.samituga.bard.type.Port;
import io.samituga.slumber.ivern.builder.Builder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ServerConfigBuilder implements Builder<ServerConfig> {


    private Port port;
    private Collection<Filter> filters = new ArrayList<>();
    private Collection<Route> routes = new ArrayList<>();
    private Collection<ExceptionHandler<? extends Exception>> exceptionHandlers = new ArrayList<>();
    private Collection<Module> jacksonModules = new ArrayList<>();

    private ServerConfigBuilder() {}

    public static ServerConfigBuilder serverConfigBuilder() {
        return new ServerConfigBuilder();
    }

    public ServerConfigBuilder port(Port port) {
        this.port = port;
        return this;
    }

    public ServerConfigBuilder filter(Filter filter) {
        this.filters.add(filter);
        return this;
    }

    public ServerConfigBuilder filters(Filter... filters) {
        this.filters.addAll(List.of(filters));
        return this;
    }

    public ServerConfigBuilder filters(Collection<Filter> filters) {
        this.filters = filters;
        return this;
    }

    public ServerConfigBuilder routes(Route route) {
        this.routes.add(route);
        return this;
    }

    public ServerConfigBuilder routes(Route... routes) {
        this.routes.addAll(List.of(routes));
        return this;
    }

    public ServerConfigBuilder routes(Collection<Route> routes) {
        this.routes = routes;
        return this;
    }

    public ServerConfigBuilder exceptionHandler(ExceptionHandler<?> exceptionHandler) {
        this.exceptionHandlers.add(exceptionHandler);
        return this;
    }

    public ServerConfigBuilder exceptionHandlers(ExceptionHandler<?>... exceptionHandlers) {
        this.exceptionHandlers.addAll(List.of(exceptionHandlers));
        return this;
    }

    public ServerConfigBuilder exceptionHandlers(Collection<ExceptionHandler<? extends Exception>> exceptionHandlers) {
        this.exceptionHandlers = exceptionHandlers;
        return this;
    }

    public ServerConfigBuilder jacksonModules(Collection<Module> jacksonModules) {
        this.jacksonModules = jacksonModules;
        return this;
    }

    @Override
    public ServerConfig build() {
        return new ServerConfigStruct(port, filters, routes, exceptionHandlers, jacksonModules);
    }
}

