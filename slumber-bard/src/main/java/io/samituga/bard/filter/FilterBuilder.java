package io.samituga.bard.filter;

import io.samituga.bard.endpoint.context.HttpContext;
import io.samituga.bard.filter.type.Order;
import io.samituga.bard.type.Path;
import io.samituga.slumber.ivern.builder.Builder;
import java.util.Optional;
import java.util.function.Function;

public class FilterBuilder implements Builder<Filter> {
    private Order order;
    private Path path;
    private Optional<Function<HttpContext, HttpContext>> doBefore = Optional.empty();
    private Optional<Function<HttpContext, HttpContext>> doAfter = Optional.empty();

    private FilterBuilder() {}

    public static FilterBuilder filterBuilder() {
        return new FilterBuilder();
    }

    public FilterBuilder order(Order order) {
        this.order = order;
        return this;
    }

    public FilterBuilder path(Path path) {
        this.path = path;
        return this;
    }

    public FilterBuilder doBefore(Function<HttpContext, HttpContext> doBefore) {
        this.doBefore = Optional.of(doBefore);
        return this;
    }

    public FilterBuilder doBefore(Optional<Function<HttpContext, HttpContext>> doBefore) {
        this.doBefore = doBefore;
        return this;
    }

    public FilterBuilder doAfter(Function<HttpContext, HttpContext> doAfter) {
        this.doAfter = Optional.of(doAfter);
        return this;
    }

    public FilterBuilder doAfter(
          Optional<Function<HttpContext, HttpContext>> doAfter) {
        this.doAfter = doAfter;
        return this;
    }


    public Filter build() {
        return new FilterStruct(order, path, doBefore, doAfter);
    }
}
