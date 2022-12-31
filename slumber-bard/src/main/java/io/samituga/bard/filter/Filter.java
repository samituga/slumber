package io.samituga.bard.filter;


import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

/**
 * Represents the web server filter.
 */
public interface Filter {

    /**
     * <p>
     * Order of execution of the filter, the lower the order it will execute first the
     * {@link #doBefore(ServletRequest, ServletResponse) doBefore} and last the
     * {@link #doAfter(ServletRequest, ServletResponse) doAfter}, the opposite for the higher order.
     * </p>
     * <p>
     * There can only be one filter with {@link Precedence#FIRST first precedence}
     * and one with {@link Precedence#LAST last precedence}.
     * </p>
     *
     * @return the order of execution of the filter.
     */
    Order order();

    /**
     * Operation to be executed before the request reaches the handler.
     *
     * @param request  the request
     * @param response the response
     */
    void doBefore(ServletRequest request, ServletResponse response);

    /**
     * Operation to be executed after the request leaves the handler.
     *
     * @param request  the request
     * @param response the response
     */
    void doAfter(ServletRequest request, ServletResponse response);
}
