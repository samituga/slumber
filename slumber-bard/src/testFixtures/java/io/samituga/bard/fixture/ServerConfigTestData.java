package io.samituga.bard.fixture;

import static io.samituga.bard.fixture.FilterTestData.aFilter;
import static io.samituga.slumber.heimer.validator.AssertionUtility.required;
import static io.samituga.slumber.heimer.validator.AssertionUtility.requiredValidPort;

import io.samituga.bard.configuration.ServerConfig;
import io.samituga.bard.endpoint.Route;
import io.samituga.bard.filter.Filter;
import io.samituga.bard.filter.Precedence;
import io.samituga.slumber.heimer.validator.AssertionUtility;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ServerConfigTestData {


    public static ServerConfigBuilder aServerConfig() {
        return serverConfigBuilder()
              .port(8080)
              .filter(aFilter().build());
    }

    public static ServerConfigBuilder serverConfigBuilder() {
        return new ServerConfigBuilder();
    }

    public static class ServerConfigBuilder {

        private int port;
        private Collection<Filter> filters = new ArrayList<>();
        private Collection<Route> routes = new ArrayList<>();

        private ServerConfigBuilder() {}

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

        public ServerConfig build() {
            return build(false);
        }

        public ServerConfig build(boolean skipValidation) {
            if (!skipValidation) {
                validate(this);
            }
            return new ServerConfig() {
                @Override
                public int port() {
                    return port;
                }

                @Override
                public Collection<Filter> filters() {
                    return filters;
                }

                @Override
                public Collection<Route> routes() {
                    return routes;
                }
            };
        }
    }

    private static void validate(ServerConfigBuilder builder) {
        validatePort(builder.port);
        validateFiltersOrder(builder.filters);
    }

    private static void validatePort(int port) {
        requiredValidPort(port);
    }

    private static void validateFiltersOrder(Collection<Filter> filters) {
        required("filters", filters);

        var firstFilterCount = filters.stream()
              .filter(filter -> filter.order().value().equals(Precedence.FIRST.precedenceLevel()))
              .count();

        var lastFilterCount = filters.stream()
              .filter(filter -> filter.order().value().equals(Precedence.LAST.precedenceLevel()))
              .count();

        AssertionUtility.validate(() -> firstFilterCount > 1,
              "Can only contain one filter with first precedence level");
        AssertionUtility.validate(() -> lastFilterCount > 1,
              "Can only contain one filter with last precedence level");
    }
}
