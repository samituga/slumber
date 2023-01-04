package io.samituga.bard.filter.type;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class OrderTest {

    @Test
    void should_throw_exception_when_using_min_integer_for_int_initializer() {
        Assertions.assertThatThrownBy(() -> Order.of(Integer.MIN_VALUE))
              .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void should_throw_exception_when_using_max_integer_for_int_initializer() {
        Assertions.assertThatThrownBy(() -> Order.of(Integer.MAX_VALUE))
              .isInstanceOf(IllegalStateException.class);
    }
}