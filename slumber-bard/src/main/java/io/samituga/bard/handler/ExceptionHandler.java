package io.samituga.bard.handler;

import io.samituga.bard.endpoint.context.HttpContext;

public interface ExceptionHandler<T extends Exception> {

    Class<T> exceptionClass();

    void handler(T exception, HttpContext httpContext);
}