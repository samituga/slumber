package io.samituga.bard.configuration;

import io.samituga.bard.endpoint.route.Route;
import io.samituga.bard.filter.Filter;
import io.samituga.slumber.ivern.builder.Builder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ServerConfigBuilder implements Builder<ServerConfig> {


    private int port;
    private Collection<Filter> filters = new ArrayList<>();
    private Collection<Route> routes = new ArrayList<>();

    private ServerConfigBuilder() {}

    public static ServerConfigBuilder serverConfigBuilder() {
        return new ServerConfigBuilder();
    }

    public ServerConfigBuilder port(int port) {
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

    @Override
    public ServerConfig build() {
        return new ServerConfigStruct(port, filters, routes);
    }
}

