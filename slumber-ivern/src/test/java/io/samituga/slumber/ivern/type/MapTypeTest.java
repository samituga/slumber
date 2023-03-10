package io.samituga.slumber.ivern.type;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MapTypeTest {


    @Test
    void should_return_empty_map() {
        var emptyMap = new MapType<String, String>() {};

        assertThat(emptyMap.isEmpty()).isTrue();
    }

    @Test
    void should_return_filled_map() {
        var map = new MapType<>(Map.of("key", "value")) {};

        assertThat(map.isEmpty()).isFalse();
    }

    @Test
    void should_throw_exception_when_calling_get_and_key_doesnt_exist() {
        var map = new MapType<>(Map.of("key", "value")) {};

        assertThatThrownBy(() -> map.get("Non existent key")).isInstanceOf(
              NullPointerException.class);
    }

    @Test
    void should_return_empty_when_calling_find_and_key_doesnt_exist() {
        var map = new MapType<>(Map.of("key", "value")) {};

        var result = map.find("Non existent key");

        assertThat(result).isEmpty();
    }
}