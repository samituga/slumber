package io.samituga.slumber.bard.javalin;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.samituga.bard.configuration.ServerConfig;
import io.samituga.bard.endpoint.Request;
import io.samituga.bard.endpoint.Response;
import io.samituga.bard.endpoint.Route;
import io.samituga.bard.filter.Filter;
import io.samituga.slumber.bard.javalin.mapper.RequestMapper;
import io.samituga.slumber.bard.javalin.mapper.VerbToHandlerType;
import io.samituga.slumber.ivern.http.type.Headers;
import java.util.Collection;
import java.util.function.Function;

public class JavalinConfigurator {


    public static void configure(Javalin javalin, ServerConfig serverConfig) {
        addFilters(javalin, serverConfig.filters());
        addRoutes(javalin, serverConfig.routes());
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
                      javalin.after(filter.path().value(),
                            ctx -> doAfterConsumer.accept(ctx.req(), ctx.res()));
                  }
              });
    }

    public static void addRoutes(Javalin javalin, Collection<Route> routes) {
        for (Route route : routes) {
            javalin.addHandler(VerbToHandlerType.toHandlerType(route.verb()), route.path().value(),
                  converToHandler(route.handler()));
        }
    }

    private static <T> Handler converToHandler(Function<Request, Response> function) {
        return ctx -> {
            final var response = function.apply(RequestMapper.fromContext(ctx));

            if (response.responseBody().isPresent()) {
                ctx.result(response.responseBody().get().value());
            }
            ctx.status(response.statusCode().code());
            ctxWithHeaders(ctx, response.headers());
        };
    }

    private static void ctxWithHeaders(Context ctx, Headers headers) {
        headers.value().forEach(ctx::header);
    }
}
