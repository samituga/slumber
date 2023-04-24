package io.samituga.bard.handler;

import io.samituga.bard.endpoint.context.HttpContext;

public interface ExceptionHandler<T extends Exception> { // TODO: 23/04/2023 Could it be a functional interface?

    Class<T> exceptionClass();

    void handle(T exception, HttpContext httpContext);
}
