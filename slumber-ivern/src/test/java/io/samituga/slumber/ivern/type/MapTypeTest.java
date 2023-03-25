package io.samituga.slumber.ivern.type;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Map;
import org.junit.jupiter.api.Test;

class MapTypeTest {


    @Test
    void should_return_empty_map() {
        // given when
        var emptyMap = new MapType<String, String>() {};

        // then
        assertThat(emptyMap.isEmpty()).isTrue();
    }

    @Test
    void should_return_filled_map() {
        // given when
        var map = new MapType<>(Map.of("key", "value")) {};

        // then
        assertThat(map.isEmpty()).isFalse();
    }

    @Test
    void should_throw_exception_when_calling_get_and_key_doesnt_exist() {
        // given when
        var map = new MapType<>(Map.of("key", "value")) {};

        // then
        assertThatThrownBy(() -> map.get("Non existent key")).isInstanceOf(
              NullPointerException.class);
    }

    @Test
    void should_return_empty_when_calling_find_and_key_doesnt_exist() {
        // given
        var map = new MapType<>(Map.of("key", "value")) {};

        // when
        var result = map.find("Non existent key");

        // then
        assertThat(result).isEmpty();
    }
}
