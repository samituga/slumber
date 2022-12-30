package io.samituga.slumber.validator;

import static io.samituga.slumber.heimer.validator.AssertionUtility.notBlank;
import static io.samituga.slumber.heimer.validator.AssertionUtility.required;
import static io.samituga.slumber.heimer.validator.AssertionUtility.requiredNotEmpty;
import static io.samituga.slumber.heimer.validator.ValidatorMessageFormat.NOT_BLANK;
import static io.samituga.slumber.heimer.validator.ValidatorMessageFormat.REQUIRED;
import static io.samituga.slumber.heimer.validator.ValidatorMessageFormat.REQUIRED_NOT_EMPTY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


import io.samituga.slumber.heimer.exception.AssertionException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class AssertionUtilityTest {


    @ParameterizedTest
    @MethodSource("io.samituga.slumber.validator.ValidatorDataProvider#null_and_blank_strings")
    void should_fail_not_empty_validation_when_strings_are_invalid(String paramName, String value) {
        String expectedMessage = String.format(NOT_BLANK.format(), paramName);

        assertThatThrownBy(() -> notBlank(paramName, value))
              .isInstanceOf(AssertionException.class)
              .hasMessage(expectedMessage);
    }

    @ParameterizedTest
    @MethodSource("io.samituga.slumber.validator.ValidatorDataProvider#valid_strings")
    void should_return_value_when_strings_are_valid(String paramName, String value) {
        String result = notBlank(paramName, value);

        assertThat(result).isEqualTo(value);
    }

    @Test
    void should_fail_required_validation_when_value_is_null() {
        var paramName = "paramName";

        String expectedMessage = String.format(REQUIRED.format(), paramName);

        assertThatThrownBy(() -> required(paramName, null))
              .isInstanceOf(AssertionException.class)
              .hasMessage(expectedMessage);
    }

    @Test
    void should_return_value_when_required_is_valid() {
        var paramName = "paramName";
        var value = "value";
        String result = required(paramName, value);

        assertThat(result).isEqualTo(value);
    }

    @ParameterizedTest
    @MethodSource("io.samituga.slumber.validator.ValidatorDataProvider#null_and_empty_collection")
    <T> void should_fail_required_and_not_empty_validation_when_collection_is_invalid(
          String paramName, Collection<T> value) {
        String expectedMessage = String.format(REQUIRED_NOT_EMPTY.format(), paramName);

        assertThatThrownBy(() -> requiredNotEmpty(paramName, value))
              .isInstanceOf(AssertionException.class)
              .hasMessage(expectedMessage);
    }

    @Test
    void should_return_value_when_collection_is_valid() {
        var validCollection = List.of("value");
        var result = requiredNotEmpty("validCollection", validCollection);

        assertThat(result).isEqualTo(validCollection);
    }

    @ParameterizedTest
    @MethodSource("io.samituga.slumber.validator.ValidatorDataProvider#null_and_empty_and_null_value_map")
    <K, V> void should_fail_required_and_not_empty_validation_when_map_is_invalid(String paramName,
                                                                                  Map<K, V> value) {
        String expectedMessage = String.format(REQUIRED_NOT_EMPTY.format(), paramName);

        assertThatThrownBy(() -> requiredNotEmpty(paramName, value))
              .isInstanceOf(AssertionException.class)
              .hasMessage(expectedMessage);
    }

    @Test
    void should_return_value_when_map_is_valid() {
        var validCollection = Map.of("Key", "value");
        var result = requiredNotEmpty("validCollection", validCollection);

        assertThat(result).isEqualTo(validCollection);
    }
}
