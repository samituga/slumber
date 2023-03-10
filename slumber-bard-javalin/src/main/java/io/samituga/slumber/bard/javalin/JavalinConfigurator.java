package io.samituga.slumber.bard.javalin;

import io.javalin.Javalin;
import io.javalin.http.Handler;
import io.samituga.bard.configuration.ServerConfig;
import io.samituga.bard.endpoint.Request;
import io.samituga.bard.endpoint.Response;
import io.samituga.bard.endpoint.Route;
import io.samituga.bard.filter.Filter;
import io.samituga.slumber.bard.javalin.mapper.RequestMapper;
import io.samituga.slumber.bard.javalin.mapper.VerbToHandlerType;
import java.util.Collection;
import java.util.function.Function;

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
            javalin.addHandler(VerbToHandlerType.toHandlerType(route.verb()), route.path().value(),
                  converToHandler(route.handler()));
        }
    }

    private static <T> Handler converToHandler(Function<Request, Response<T>> function) {
        return context -> {
            final var response = function.apply(RequestMapper.fromContext(context));

            if (response.responseBody().isPresent()) {
                context.result(response.responseBody().get().toString()); // TODO: 05/02/2023 Bytes?
            }
            context.status(response.statusCode().code());
        };
    }
}
