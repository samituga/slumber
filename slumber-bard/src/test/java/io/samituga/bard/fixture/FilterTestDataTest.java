package io.samituga.bard.fixture;

import static io.samituga.bard.filter.FilterBuilder.filterBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.samituga.bard.filter.type.Order;
import io.samituga.bard.type.Path;
import io.samituga.slumber.heimer.validator.exception.AssertionException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.function.BiConsumer;
import org.junit.jupiter.api.Test;

class FilterTestDataTest {

    private static final Order ORDER = Order.of(0);
    private static final Path PATH = Path.of("**");

    private static final BiConsumer<HttpServletRequest, HttpServletResponse> DO_BEFORE =
          (req, res) -> System.out.println("before");
    private static final BiConsumer<HttpServletRequest, HttpServletResponse> DO_AFTER =
          (req, res) -> System.out.println("after");

    @Test
    void should_build_filter() {
        // given when
        var result = filterBuilder()
              .order(ORDER)
              .path(PATH)
              .doBefore(DO_BEFORE)
              .doAfter(DO_AFTER)
              .build();

        // then
        assertThat(result).isNotNull();
        assertThat(result.order()).isEqualTo(ORDER);
        assertThat(result.path()).isEqualTo(PATH);
        assertThat(result.doBefore()).isPresent().get().isEqualTo(DO_BEFORE);
        assertThat(result.doAfter()).isPresent().get().isEqualTo(DO_AFTER);
    }

    @Test
    void should_build_filter_when_do_before_or_do_after_is_not_present() {
        // given when
        var result = filterBuilder()
              .order(ORDER)
              .path(PATH)
              .build();

        // then
        assertThat(result).isNotNull();
        assertThat(result.order()).isEqualTo(ORDER);
        assertThat(result.path()).isEqualTo(PATH);
        assertThat(result.doBefore()).isEmpty();
        assertThat(result.doAfter()).isEmpty();
    }

    @Test
    void should_throw_exception_if_order_is_null() {
        // given
        var builder = filterBuilder()
              .order(null)
              .path(PATH)
              .doBefore(DO_BEFORE)
              .doAfter(DO_AFTER);

        // when then
        assertThatThrownBy(builder::build)
              .isInstanceOf(AssertionException.class);
    }

    @Test
    void should_throw_exception_if_path_is_null() {
        // given
        var builder = filterBuilder()
              .order(ORDER)
              .path(null)
              .doBefore(DO_BEFORE)
              .doAfter(DO_AFTER);

        // when then
        assertThatThrownBy(builder::build)
              .isInstanceOf(AssertionException.class);
    }
}
