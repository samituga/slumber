package io.samituga.slumber.ivern.http.type;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Map;
import io.samituga.slumber.heimer.validator.exception.AssertionException;
import org.junit.jupiter.api.Test;

class HeadersTest {


    @Test
    void should_instantiate_headers_correctly_from_varargs() {
        // given
        var key1 = "key1";
        var value1 = "value1";
        var key2 = "key2";
        var value2 = "value2";

        var expected = Map.of(key1, value1, key2, value2);

        // when
        var result = Headers.of(key1, value1, key2, value2);

        // then
        assertThat(result.value()).containsExactlyInAnyOrderEntriesOf(expected);
    }

    @Test
    void should_throw_exception_when_varargs_size_is_odd() {
        // given
        var key1 = "key1";
        var value1 = "value1";
        var key2 = "key2";

        // when then
        assertThatThrownBy(() -> Headers.of(key1, value1, key2))
              .isInstanceOf(AssertionException.class);
    }

    @Test
    void should_create_new_instance_with_previous_and_new_values() {
        // given
        var key1 = "key1";
        var value1 = "value1";
        var key2 = "key2";
        var value2 = "value2";
        var headers = Headers.of(key1, value1, key2, value2);

        var key3 = "key3";
        var value3 = "value3";
        var key4 = "key4";
        var value4 = "value4";

        // when
        var result = headers.withHeaders(key3, value3, key4, value4);

        // then
        var expected = Map.of(key1, value1, key2, value2, key3, value3, key4, value4);
        assertThat(result.value()).containsExactlyInAnyOrderEntriesOf(expected);
    }
}
