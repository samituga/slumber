package io.samituga.slumber.bard.javalin.configurator;

import static io.samituga.slumber.bard.javalin.mapper.HttpContextMapper.fromJavalinContext;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.samituga.bard.configuration.ServerConfig;
import io.samituga.bard.endpoint.context.HttpContext;
import io.samituga.bard.endpoint.response.HttpResponse;
import io.samituga.bard.endpoint.response.ResponseBody;
import io.samituga.bard.endpoint.response.type.ByteResponseBody;
import io.samituga.bard.endpoint.response.type.InputStreamResponseBody;
import io.samituga.bard.endpoint.route.Route;
import io.samituga.bard.exception.UnsupportedResponseTypeException;
import io.samituga.bard.filter.Filter;
import io.samituga.bard.handler.ExceptionHandler;
import io.samituga.slumber.bard.javalin.mapper.VerbToHandlerType;
import io.samituga.slumber.ivern.http.type.Headers;
import java.util.Collection;
import java.util.function.Function;

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
                        javalin.before(filter.path().value(),
                              ctx -> {
                                  var httpContext = doBefore.apply(fromJavalinContext(ctx));
                                  withResponse(ctx, httpContext.response());
                              }));
                  filter.doAfter().ifPresent(doAfter ->
                        javalin.after(
                              filter.path().value(),
                              ctx -> {
                                  var httpContext = doAfter.apply(fromJavalinContext(ctx));
                                  withResponse(ctx, httpContext.response());
                              }));
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
                var response =
                      ((ExceptionHandler<Exception>) exceptionHandler).handle(e, httpContext);
                withResponse(ctx, response.response());
            });
        }
    }

    private static Handler handle(Function<HttpContext, HttpContext> function) {
        return ctx -> {
            var httpContext = fromJavalinContext(ctx);
            httpContext = function.apply(httpContext);
            var response = httpContext.response();
            withResponse(ctx, response);
        };
    }

    private static void withResponse(Context ctx, HttpResponse response) {
        if (response.responseBody().isPresent()) {
            withResponseBody(ctx, response.responseBody().get());
        }
        ctx.status(response.statusCode().code());
        withResponseHeaders(ctx, response.headers());
    }

    private static void withResponseBody(Context ctx, ResponseBody responseBody) {
        if (responseBody instanceof InputStreamResponseBody inputStreamResponseBody) {
            ctx.result(inputStreamResponseBody.responseBody());
        } else if (responseBody instanceof ByteResponseBody byteResponseBody) {
            ctx.result(byteResponseBody.responseBody());
        } else {
            throw new UnsupportedResponseTypeException();
        }
    }

    private static void withResponseHeaders(Context ctx, Headers headers) {
        headers.value().forEach(ctx::header);
    }
}
