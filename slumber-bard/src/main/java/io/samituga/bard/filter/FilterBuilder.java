package io.samituga.bard.filter;

import io.samituga.bard.filter.type.Order;
import io.samituga.bard.type.Path;
import io.samituga.slumber.ivern.builder.Builder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.function.BiConsumer;

public class FilterBuilder implements Builder<Filter> {
    private Order order;
    private Path path;
    private Optional<BiConsumer<HttpServletRequest, HttpServletResponse>> doBefore = Optional.empty();
    private Optional<BiConsumer<HttpServletRequest, HttpServletResponse>> doAfter = Optional.empty();

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

    public FilterBuilder doBefore(BiConsumer<HttpServletRequest, HttpServletResponse> doBefore) {
        this.doBefore = Optional.of(doBefore);
        return this;
    }

    public FilterBuilder doBefore(
          Optional<BiConsumer<HttpServletRequest, HttpServletResponse>> doBefore) {
        this.doBefore = doBefore;
        return this;
    }

    public FilterBuilder doAfter(BiConsumer<HttpServletRequest, HttpServletResponse> doAfter) {
        this.doAfter = Optional.of(doAfter);
        return this;
    }

    public FilterBuilder doAfter(
          Optional<BiConsumer<HttpServletRequest, HttpServletResponse>> doAfter) {
        this.doAfter = doAfter;
        return this;
    }


    public Filter build() {
        return new FilterStruct(order, path, doBefore, doAfter);
    }
}
