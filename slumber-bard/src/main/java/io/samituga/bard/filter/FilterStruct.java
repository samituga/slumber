package io.samituga.bard.filter;

import static io.samituga.bard.filter.FilterBuilder.filterBuilder;
import static io.samituga.slumber.heimer.validator.AssertionUtility.required;

import io.samituga.bard.filter.type.Order;
import io.samituga.bard.type.Path;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.function.BiConsumer;

record FilterStruct(Order order, Path path,
                    Optional<BiConsumer<HttpServletRequest, HttpServletResponse>> doBefore,
                    Optional<BiConsumer<HttpServletRequest, HttpServletResponse>> doAfter)
      implements Filter {

    FilterStruct(Order order, Path path,
                 Optional<BiConsumer<HttpServletRequest, HttpServletResponse>> doBefore,
                 Optional<BiConsumer<HttpServletRequest, HttpServletResponse>> doAfter) {
        this.order = required("order", order);
        this.path = required("path", path);
        this.doBefore = required("doBefore", doBefore);
        this.doAfter = required("doAfter", doAfter);
    }

    @Override
    public int compareTo(Filter o) {
        return order.compareTo(o.order());
    }

    @Override
    public FilterBuilder copy() {
        return filterBuilder()
              .order(order)
              .path(path)
              .doBefore(doBefore)
              .doAfter(doAfter);
    }
}
