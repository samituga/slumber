package io.samituga.bard.fixture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.samituga.bard.endpoint.type.Path;
import io.samituga.bard.filter.type.Order;
import io.samituga.slumber.heimer.validator.exception.AssertionException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.junit.jupiter.api.Test;
import java.util.function.BiConsumer;

class FilterTestDataTest {

    private static final Order ORDER = Order.of(0);
    private static final Path PATH = Path.of("**");

    private static final BiConsumer<ServletRequest, ServletResponse> DO_BEFORE =
          (req, res) -> System.out.println("before");
    private static final BiConsumer<ServletRequest, ServletResponse> DO_AFTER =
          (req, res) -> System.out.println("after");

    @Test
    void should_build_filter() {

        var result = FilterTestData.filterBuilder()
              .order(ORDER)
              .path(PATH)
              .doBefore(DO_BEFORE)
              .doAfter(DO_AFTER)
              .build();

        assertThat(result).isNotNull();
        assertThat(result.order()).isEqualTo(ORDER);
        assertThat(result.path()).isEqualTo(PATH);
        assertThat(result.doBefore()).isPresent().get().isEqualTo(DO_BEFORE);
        assertThat(result.doAfter()).isPresent().get().isEqualTo(DO_AFTER);
    }

    @Test
    void should_build_filter_with_invalid_data_if_skip_validation_is_true() {

        var result = FilterTestData.filterBuilder()
              .order(null)
              .path(PATH)
              .doBefore(DO_BEFORE)
              .doAfter(DO_AFTER)
              .build(true);

        assertThat(result).isNotNull();
        assertThat(result.order()).isNull();
        assertThat(result.path()).isEqualTo(PATH);
        assertThat(result.doBefore()).isPresent().get().isEqualTo(DO_BEFORE);
        assertThat(result.doAfter()).isPresent().get().isEqualTo(DO_AFTER);
    }

    @Test
    void should_build_filter_when_do_before_or_do_after_is_not_present() {

        var result = FilterTestData.filterBuilder()
              .order(ORDER)
              .path(PATH)
              .build();

        assertThat(result).isNotNull();
        assertThat(result.order()).isEqualTo(ORDER);
        assertThat(result.path()).isEqualTo(PATH);
        assertThat(result.doBefore()).isEmpty();
        assertThat(result.doAfter()).isEmpty();
    }

    @Test
    void should_throw_exception_if_order_is_null() {
        var builder = FilterTestData.filterBuilder()
              .order(null)
              .path(PATH)
              .doBefore(DO_BEFORE)
              .doAfter(DO_AFTER);

        assertThatThrownBy(builder::build)
              .isInstanceOf(AssertionException.class);
    }

    @Test
    void should_throw_exception_if_path_is_null() {
        var builder = FilterTestData.filterBuilder()
              .order(ORDER)
              .path(null)
              .doBefore(DO_BEFORE)
              .doAfter(DO_AFTER);

        assertThatThrownBy(builder::build)
              .isInstanceOf(AssertionException.class);
    }
}
