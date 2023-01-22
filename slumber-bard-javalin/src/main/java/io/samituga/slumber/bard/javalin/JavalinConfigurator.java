package io.samituga.slumber.bard.javalin;

import io.javalin.Javalin;
import io.samituga.bard.configuration.ServerConfig;
import io.samituga.bard.endpoint.Route;
import io.samituga.bard.filter.Filter;
import java.util.Collection;

public class JavalinConfigurator {


    public static void configure(Javalin javalin, ServerConfig serverConfig) {

    }


    public static void addFilters(Javalin javalin, Collection<Filter> filters) {
        filters.stream()
              .sorted()
              .forEach(filter -> {
                  if (filter.doBefore().isPresent()) {
                      var doBeforeConsumer = filter.doBefore().get();
                      javalin.before(filter.path().value(),
                            ctx -> doBeforeConsumer.accept(ctx.req(), ctx.res()));
                  }

                  if (filter.doAfter().isPresent()) {
                      var doAfterConsumer = filter.doAfter().get();
                      javalin.before(filter.path().value(),
                            ctx -> doAfterConsumer.accept(ctx.req(), ctx.res()));
                  }
              });
    }

    public static void addRoutes(Javalin javalin, Collection<Route<?>> routes) {
        for (Route<?> route : routes) {

        }
    }
}
