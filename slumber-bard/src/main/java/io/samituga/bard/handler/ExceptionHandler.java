package io.samituga.bard.handler;

public interface ExceptionHandler<T extends Exception> {

    Class<T> exceptionClass();

    void handler(T exception);
}
