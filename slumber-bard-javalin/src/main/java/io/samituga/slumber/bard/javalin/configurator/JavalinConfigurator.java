package io.samituga.slumber.bard.javalin.configurator;

import static io.samituga.slumber.bard.javalin.mapper.HttpContextMapper.fromJavalinContext;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.samituga.bard.configuration.ServerConfig;
import io.samituga.bard.endpoint.context.HttpContext;
import io.samituga.bard.endpoint.route.Route;
import io.samituga.bard.filter.Filter;
import io.samituga.bard.handler.ExceptionHandler;
import io.samituga.slumber.bard.javalin.mapper.VerbToHandlerType;

import java.util.Collection;
import java.util.function.Consumer;

public class JavalinConfigurator {


    public static void configure(Javalin javalin, ServerConfig serverConfig) {
        withFilters(javalin, serverConfig.filters());
        withRoutes(javalin, serverConfig.routes());
        withExceptionHandlers(javalin, serverConfig.exceptionHandlers());
    }

    private static void withFilters(Javalin javalin, Collection<Filter> filters) {
        filters.stream()
              .sorted()
              .forEach(filter -> {
                  filter.doBefore().ifPresent(doBefore ->
                        javalin.before(
                              filter.path().value(),
                              ctx -> doBefore.accept(fromJavalinContext(ctx))));
                  filter.doAfter().ifPresent(doAfter ->
                        javalin.after(
                              filter.path().value(),
                              ctx -> doAfter.accept(fromJavalinContext(ctx))));
              });
    }

    private static void withRoutes(Javalin javalin, Collection<Route> routes) {
        for (Route route : routes) {
            javalin.addHandler(
                  VerbToHandlerType.toHandlerType(route.verb()),
                  route.path().value(),
                  handle(route.handler()));
        }
    }

    @SuppressWarnings("unchecked cast")
    private static void withExceptionHandlers(Javalin javalin,
                                              Collection<ExceptionHandler<? extends Exception>> exceptionHandlers) {
        for (ExceptionHandler<?> exceptionHandler : exceptionHandlers) {
            Class<? extends Exception> exceptionClass = exceptionHandler.exceptionClass();
            javalin.exception(exceptionClass, (Exception e, Context ctx) -> {
                var httpContext = fromJavalinContext(ctx);
                ((ExceptionHandler<Exception>) exceptionHandler).handle(e, httpContext);
            });
        }
    }

    private static Handler handle(Consumer<HttpContext> function) {
        return ctx -> {
            var httpContext = fromJavalinContext(ctx);
            function.accept(httpContext);
        };
    }
}
