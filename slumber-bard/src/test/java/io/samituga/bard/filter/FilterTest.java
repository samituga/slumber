package io.samituga.bard.filter;

import static org.assertj.core.api.Assertions.assertThat;

import io.samituga.bard.fixture.FilterTestData;
import org.junit.jupiter.api.Test;

class FilterTest {

    @Test
    void should_make_exact_copy() {
        // given
        var filter = FilterTestData.aFilter().build();

        // when
        var copy = filter.copy().build();

        // then
        assertThat(copy).isEqualTo(filter);
    }
}
