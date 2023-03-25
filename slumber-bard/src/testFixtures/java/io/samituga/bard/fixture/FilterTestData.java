package io.samituga.bard.fixture;

import static io.samituga.bard.filter.FilterBuilder.filterBuilder;

import io.samituga.bard.filter.FilterBuilder;
import io.samituga.bard.filter.type.Order;
import io.samituga.bard.type.Path;

public class FilterTestData {


    public static FilterBuilder aFilter() {
        return filterBuilder()
              .order(Order.of(0))
              .path(Path.of("**"));
    }
}
