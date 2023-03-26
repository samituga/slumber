package io.samituga.slumber.bard.javalin.configurator;

import static io.samituga.slumber.bard.javalin.mapper.HttpContextMapper.fromJavalinContext;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.samituga.bard.configuration.ServerConfig;
import io.samituga.bard.endpoint.context.HttpContext;
import io.samituga.bard.endpoint.response.ResponseBody;
import io.samituga.bard.endpoint.response.type.ByteResponseBody;
import io.samituga.bard.endpoint.response.type.InputStreamResponseBody;
import io.samituga.bard.endpoint.route.Route;
import io.samituga.bard.exception.UnsupportedResponseTypeException;
import io.samituga.bard.filter.Filter;
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
                            ctx -> doBeforeConsumer.accept(fromJavalinContext(ctx)));
                  }

                  if (filter.doAfter().isPresent()) {
                      var doAfterConsumer = filter.doAfter().get();
                      javalin.after(filter.path().value(),
                            ctx -> doAfterConsumer.accept(fromJavalinContext(ctx)));
                  }
              });
    }

    public static void addRoutes(Javalin javalin, Collection<Route> routes) {
        for (Route route : routes) {
            javalin.addHandler(VerbToHandlerType.toHandlerType(route.verb()), route.path().value(),
                  handle(route.handler()));
        }
    }

//    public static void addExceptionHandlers(Javalin javalin, Collection<ExceptionHandler<?>> exceptionHandlers) {
//
//        for (ExceptionHandler<?> exceptionHandler : exceptionHandlers) {
//
//            javalin.exception(exceptionHandler.exceptionClass(), (e, ctx) ->{
//                ctx.
//            });
//        }
//
//        javalin.exception()
//    }

    private static Handler handle(Function<HttpContext, HttpContext> function) {
        return ctx -> {
            var httpContext = fromJavalinContext(ctx);
            httpContext = function.apply(httpContext);
            var response = httpContext.response();
            if (response.responseBody().isPresent()) {
                handleResponseType(response.responseBody().get(), ctx);
            }
            ctx.status(response.statusCode().code());
            ctxWithHeaders(ctx, response.headers());
        };
    }

    private static void handleResponseType(ResponseBody responseBody, Context ctx) {
        if (responseBody instanceof InputStreamResponseBody inputStreamResponseBody) {
            ctx.result(inputStreamResponseBody.responseBody());
        } else if (responseBody instanceof ByteResponseBody byteResponseBody) {
            ctx.result(byteResponseBody.responseBody());
        } else {
            throw new UnsupportedResponseTypeException();
        }
    }

    private static void ctxWithHeaders(Context ctx, Headers headers) {
        headers.value().forEach(ctx::header);
    }
}
