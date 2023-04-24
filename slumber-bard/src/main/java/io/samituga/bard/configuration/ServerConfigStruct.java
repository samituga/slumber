package io.samituga.bard.configuration;

import static io.samituga.bard.configuration.ServerConfigBuilder.serverConfigBuilder;
import static io.samituga.slumber.heimer.validator.AssertionUtility.required;

import com.fasterxml.jackson.databind.Module;
import io.samituga.bard.endpoint.route.Route;
import io.samituga.bard.filter.Filter;
import io.samituga.bard.filter.Precedence;
import io.samituga.bard.handler.ExceptionHandler;
import io.samituga.bard.type.Port;
import io.samituga.slumber.heimer.validator.AssertionUtility;
import java.util.Collection;

record ServerConfigStruct(Port port,
                          Collection<Filter> filters,
                          Collection<Route> routes,
                          Collection<ExceptionHandler<? extends Exception>> exceptionHandlers,
                          Collection<Module> jacksonModules)
      implements ServerConfig {

    ServerConfigStruct(Port port,
                       Collection<Filter> filters,
                       Collection<Route> routes,
                       Collection<ExceptionHandler<? extends Exception>> exceptionHandlers,
                       Collection<Module> jacksonModules) {
        this.port = required("port", port);
        this.filters = validateFiltersOrder(filters);
        this.routes = required("routes", routes);
        this.exceptionHandlers = required("exceptionHandlers", exceptionHandlers);
        this.jacksonModules = required("jacksonModules", jacksonModules);
    }

    @Override
    public ServerConfigBuilder copy() {
        return serverConfigBuilder()
              .port(port)
              .filters(filters)
              .routes(routes)
              .exceptionHandlers(exceptionHandlers)
              .jacksonModules(jacksonModules);
    }

    // TODO: 2023-03-31 Create a Filters class and move this to that class
    private static Collection<Filter> validateFiltersOrder(Collection<Filter> filters) {
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
        return filters;
    }
}
