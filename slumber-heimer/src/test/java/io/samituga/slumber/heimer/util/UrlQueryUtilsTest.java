package io.samituga.slumber.heimer.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import java.util.Collections;
import java.util.List;
import java.util.Map;

class UrlQueryUtilsTest {

    @Test
    void should_return_empty_map_when_query_is_empty() {
        // given
        String emptyQuery = "";

        // when
        Map<String, List<String>> queryParams = UrlQueryUtils.parseQueryParams(emptyQuery);

        // then
        assertThat(queryParams).isEmpty();
    }

    @Test
    void should_return_map_with_single_param() {
        // given
        String query = "key=value";

        // when
        Map<String, List<String>> queryParams = UrlQueryUtils.parseQueryParams(query);

        // then
        assertThat(queryParams).containsEntry("key", Collections.singletonList("value"));
    }

    @Test
    void should_return_map_with_multiple_params() {
        // given
        String query = "key1=value1&key2=value2";

        // when
        Map<String, List<String>> queryParams = UrlQueryUtils.parseQueryParams(query);

        // then
        assertThat(queryParams)
              .containsEntry("key1", Collections.singletonList("value1"))
              .containsEntry("key2", Collections.singletonList("value2"));
    }

    @Test
    void should_return_map_with_multiple_values_for_same_param() {
        // given
        String query = "key=value1&key=value2";

        // when
        Map<String, List<String>> queryParams = UrlQueryUtils.parseQueryParams(query);

        // then
        assertThat(queryParams)
              .containsEntry("key", List.of("value1", "value2"));
    }

    @Test
    void should_decode_url_encoding() {
        // given
        String query = "key=value%201";

        // when
        Map<String, List<String>> queryParams = UrlQueryUtils.parseQueryParams(query);

        // then
        assertThat(queryParams).containsEntry("key", Collections.singletonList("value 1"));
    }

    @Test
    void should_throw_exception_for_invalid_url_encoding() {
        // given
        String query = "key=value%";

        // when then
        assertThatThrownBy(() -> UrlQueryUtils.parseQueryParams(query))
              .isInstanceOf(IllegalArgumentException.class);
    }
}
