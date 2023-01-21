package io.samituga.bard.endpoint;

import io.samituga.bard.endpoint.type.Path;
import io.samituga.bard.endpoint.type.PathParamName;
import io.samituga.bard.endpoint.type.QueryParamName;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;

/**
 * Returns all the data necessary for a route.
 *
 * @param <T> the type of the response.
 */
public interface Route<T> {

    /**
     * The HTTP verb that this rout handles.
     *
     * @return the HTTP verb
     */
    Verb verb();

    /**
     * The path of the HTTP request.
     *
     * @return the path of the HTTP request
     */
    Path path();

    /**
     * Provides information about the path param names that this route will use.
     * <p> Should return an {@link Collections#emptyList() empty list} if it is not applicable.
     *
     * @return the path param names.
     */
    Collection<PathParamName> pathParamNames();

    /**
     * Provides information about the query param names that this route will use.
     * <p> Should return an {@link Collections#emptyList() empty list} if it is not applicable.
     *
     * @return the query param names.
     */
    Collection<QueryParamName> queryParamNames();


    /**
     * Handler for the route, will receive the request information and will return the response
     * with or without a response body.
     *
     * @return the response.
     */
    Function<HttpServletRequest, Response<T>> handler(); // TODO: 04/01/2023 Can HttpServletRequest be used here?
}
