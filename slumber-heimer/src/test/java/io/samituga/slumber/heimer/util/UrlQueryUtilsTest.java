package io.samituga.slumber.heimer.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

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

    @Test
    void should_create_query_string_with_single_parameter() {
        // given
        Map<String, List<String>> queryMap = Map.of(
              "name", List.of("John")
        );

        // when
        String queryString = UrlQueryUtils.createQueryString(queryMap);

        // then
        assertThat(queryString).isEqualTo("name=John");
    }

    @Test
    void should_create_query_string_with_multiple_parameters() {
        // given
        Map<String, List<String>> queryMap = Map.of(
              "name", List.of("John"),
              "age", List.of("30")
        );

        // when
        String queryString = UrlQueryUtils.createQueryString(queryMap);

        // then
        assertThat(queryString)
              .contains("name=John")
              .contains("age=30")
              .hasSameSizeAs("name=John&age=30");
    }

    @Test
    void should_create_query_string_with_multiple_values_for_same_parameter() {
        // given
        Map<String, List<String>> queryMap = Map.of(
              "name", List.of("John", "Doe")
        );

        // when
        String queryString = UrlQueryUtils.createQueryString(queryMap);

        // then
        assertThat(queryString)
              .contains("name=John")
              .contains("name=Doe")
              .hasSameSizeAs("name=John&name=Doe");
    }

    @Test
    void should_create_query_string_with_multiple_values_for_different_parameters() {
        // given
        Map<String, List<String>> queryMap = Map.of(
              "name", List.of("John", "Doe"),
              "age", List.of("30", "40")
        );

        // when
        String queryString = UrlQueryUtils.createQueryString(queryMap);

        // then
        assertThat(queryString)
              .contains("name=John")
              .contains("name=Doe")
              .contains("age=30")
              .contains("age=40")
              .hasSameSizeAs("name=John&name=Doe&age=30&age=40");
    }

    @Test
    void should_create_query_string_with_special_characters() {
        // given
        Map<String, List<String>> queryMap = Map.of(
              "name", List.of("John Doe"),
              "age", List.of("30+")
        );

        // when
        String queryString = UrlQueryUtils.createQueryString(queryMap);

        // then
        assertThat(queryString)
              .contains("name=John+Doe")
              .contains("age=30%2B")
              .hasSameSizeAs("name=John+Doe&age=30%2B");
    }

    @Test
    void should_create_empty_query_string() {
        // given
        Map<String, List<String>> queryMap = Collections.emptyMap();

        // when
        String queryString = UrlQueryUtils.createQueryString(queryMap);

        // then
        assertThat(queryString).isEmpty();
    }

}
