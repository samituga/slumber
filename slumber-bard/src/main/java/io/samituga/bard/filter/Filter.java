package io.samituga.bard.filter;


import io.samituga.bard.endpoint.context.HttpContext;
import io.samituga.bard.filter.type.Order;
import io.samituga.bard.type.Path;
import io.samituga.slumber.ivern.structure.Structure;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Interface representing a web server filter.
 */
public interface Filter extends Comparable<Filter>, Structure<Filter, FilterBuilder> {

    /**
     * <p>
     * Order of execution of the filter, the lower the order it will execute first the
     * {@link #doBefore() doBefore} and last the
     * {@link #doAfter() doAfter}, the opposite for the higher order.
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
     * The ant path matcher for the requests where this filter will operate.
     *
     * @return the path
     */
    Path path();

    /**
     * Operation to be executed before the request reaches the handler.
     *
     * @return A consumer that will do the filtering before the request,
     *       {@link Optional#empty() empty} if there is nothing to do before
     */
    Optional<Consumer<HttpContext>> doBefore();

    /**
     * Operation to be executed after the request leaves the handler.
     *
     * @return A consumer that will do the filtering after the request,
     *       {@link Optional#empty() empty} if there is nothing to do after
     */
    Optional<Consumer<HttpContext>> doAfter();
}
