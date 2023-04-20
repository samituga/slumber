package io.samituga.bard.path;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.samituga.bard.path.exception.DuplicatedParamNamesException;
import org.junit.jupiter.api.Test;

import java.util.Map;

class PathParserTest {

    @Test
    void should_throw_exception_for_invalid_params() {
        // given
        String rawPath = "/some/path/:invalidParam";

        // when
        // then
        assertThatThrownBy(() -> new PathParser(rawPath))
              .isInstanceOf(IllegalArgumentException.class)
              .hasMessage("Path '" + rawPath
                    + "' is invalid - params should be defined as '{param}' or <param>.");
    }

    @Test
    void should_throw_exception_for_non_unique_param_names() {
        // given
        String rawPath = "/some/path/{param}/{param}";

        // when
        // then
        assertThatThrownBy(() -> new PathParser(rawPath))
              .isInstanceOf(DuplicatedParamNamesException.class);
    }

    @Test
    void should_match_valid_url() {
        // given
        String rawPath = "/some/path/{param}/test";
        PathParser pathParser = new PathParser(rawPath);
        String validUrl = "/some/path/value/test";

        // when
        boolean matches = pathParser.matches(validUrl);

        // then
        assertThat(matches).isTrue();
    }

    @Test
    void should_not_match_invalid_url() {
        // given
        String rawPath = "/some/path/{param}/test";
        PathParser pathParser = new PathParser(rawPath);
        String invalidUrl = "/some/other/path/value/test";

        // when
        boolean matches = pathParser.matches(invalidUrl);

        // then
        assertThat(matches).isFalse();
    }

    @Test
    void should_extract_path_params() {
        // given
        String rawPath = "/some/path/{param1}/test/{param2}";
        PathParser pathParser = new PathParser(rawPath);
        String url = "/some/path/value1/test/value2";

        // when
        Map<String, String> pathParams = pathParser.extractPathParams(url);

        // then
        assertThat(pathParams)
              .containsEntry("param1", "value1")
              .containsEntry("param2", "value2");
    }

    @Test
    void should_return_empty_map_when_path_params_not_found() {
        // given
        String rawPath = "/some/path/{param1}/test/{param2}";
        PathParser pathParser = new PathParser(rawPath);
        String url = "/some/other/path/value1/test/value2";

        // when
        Map<String, String> pathParams = pathParser.extractPathParams(url);

        // then
        assertThat(pathParams).isEmpty();
    }
}
