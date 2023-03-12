package io.samituga.bard.fixture;

import io.samituga.bard.endpoint.type.Path;
import io.samituga.bard.filter.Filter;
import io.samituga.bard.filter.type.Order;
import io.samituga.slumber.heimer.validator.AssertionUtility;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;
import java.util.function.BiConsumer;

public class FilterTestData {


    public static FilterTestData.FilterBuilder aFilter() {
        return filterBuilder()
              .order(Order.of(0))
              .path(Path.of("**"));
    }

    public static FilterTestData.FilterBuilder filterBuilder() {
        return new FilterTestData.FilterBuilder();
    }

    public static class FilterBuilder {
        private Order order;
        private Path path;
        private Optional<BiConsumer<HttpServletRequest, HttpServletResponse>> doBefore = Optional.empty();
        private Optional<BiConsumer<HttpServletRequest, HttpServletResponse>> doAfter = Optional.empty();

        private FilterBuilder() {}

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

        public FilterBuilder doAfter(BiConsumer<HttpServletRequest, HttpServletResponse> doAfter) {
            this.doAfter = Optional.of(doAfter);
            return this;
        }
        public Filter build() {
            return build(false);
        }

        public Filter build(boolean skipValidation) {
            if (!skipValidation) {
                validate(this);
            }
            return new Filter() {
                @Override
                public Order order() {
                    return order;
                }

                @Override
                public Path path() {
                    return path;
                }

                @Override
                public Optional<BiConsumer<HttpServletRequest, HttpServletResponse>> doBefore() {
                    return doBefore;
                }

                @Override
                public Optional<BiConsumer<HttpServletRequest, HttpServletResponse>> doAfter() {
                    return doAfter;
                }

                @Override
                public int compareTo(Filter o) {
                    return order.compareTo(o.order());
                }
            };
        }
    }

    private static void validate(FilterBuilder builder) {
        validateOrder(builder.order);
        validatePath(builder.path);
    }

    private static void validateOrder(Order order) {
        AssertionUtility.required("order", order);
    }

    private static void validatePath(Path path) {
        AssertionUtility.required("path", path);
    }
}
