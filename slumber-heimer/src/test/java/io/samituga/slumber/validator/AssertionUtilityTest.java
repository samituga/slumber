package io.samituga.slumber.validator;

import static io.samituga.slumber.heimer.validator.AssertionUtility.required;
import static io.samituga.slumber.heimer.validator.AssertionUtility.requiredArgsPair;
import static io.samituga.slumber.heimer.validator.AssertionUtility.requiredNotBlank;
import static io.samituga.slumber.heimer.validator.AssertionUtility.requiredNotEmpty;
import static io.samituga.slumber.heimer.validator.AssertionUtility.requiredValidPort;
import static io.samituga.slumber.heimer.validator.ValidatorMessageFormat.NOT_BLANK;
import static io.samituga.slumber.heimer.validator.ValidatorMessageFormat.REQUIRED;
import static io.samituga.slumber.heimer.validator.ValidatorMessageFormat.REQUIRED_EVEN_VALUES;
import static io.samituga.slumber.heimer.validator.ValidatorMessageFormat.REQUIRED_NOT_EMPTY;
import static io.samituga.slumber.heimer.validator.ValidatorMessageFormat.REQUIRED_VALID_PORT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.samituga.slumber.heimer.validator.exception.AssertionException;
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
        // given
        String expectedMessage = String.format(NOT_BLANK.format(), paramName);

        // when then
        assertThatThrownBy(() -> requiredNotBlank(paramName, value))
              .isInstanceOf(AssertionException.class)
              .hasMessage(expectedMessage);
    }

    @ParameterizedTest
    @MethodSource("io.samituga.slumber.validator.ValidatorDataProvider#valid_strings")
    void should_return_value_when_strings_are_valid(String paramName, String value) {
        // given when
        String result = requiredNotBlank(paramName, value);

        // then
        assertThat(result).isEqualTo(value);
    }

    @Test
    void should_fail_required_validation_when_value_is_null() {
        // given
        String paramName = "paramName";

        String expectedMessage = String.format(REQUIRED.format(), paramName);

        // when then
        assertThatThrownBy(() -> required(paramName, null))
              .isInstanceOf(AssertionException.class)
              .hasMessage(expectedMessage);
    }

    @Test
    void should_return_value_when_required_is_valid() {
        // given
        var paramName = "paramName";
        var value = "value";

        // when
        String result = required(paramName, value);

        // then
        assertThat(result).isEqualTo(value);
    }

    @ParameterizedTest
    @MethodSource("io.samituga.slumber.validator.ValidatorDataProvider#null_and_empty_collection")
    <T> void should_fail_required_and_not_empty_validation_when_collection_is_invalid(
          String paramName, Collection<T> value) {
        // given
        String expectedMessage = String.format(REQUIRED_NOT_EMPTY.format(), paramName);

        // when then
        assertThatThrownBy(() -> requiredNotEmpty(paramName, value))
              .isInstanceOf(AssertionException.class)
              .hasMessage(expectedMessage);
    }

    @Test
    void should_return_value_when_collection_is_valid() {
        // given
        var validCollection = List.of("value");

        // when
        var result = requiredNotEmpty("validCollection", validCollection);

        // then
        assertThat(result).isEqualTo(validCollection);
    }

    @ParameterizedTest
    @MethodSource("io.samituga.slumber.validator.ValidatorDataProvider#null_and_empty_and_null_value_map")
    <K, V> void should_fail_required_and_not_empty_validation_when_map_is_invalid(String paramName,
                                                                                  Map<K, V> value) {
        // given
        String expectedMessage = String.format(REQUIRED_NOT_EMPTY.format(), paramName);

        // when then
        assertThatThrownBy(() -> requiredNotEmpty(paramName, value))
              .isInstanceOf(AssertionException.class)
              .hasMessage(expectedMessage);
    }

    @Test
    void should_return_value_when_map_is_valid() {
        // given
        var validCollection = Map.of("Key", "value");

        // when
        var result = requiredNotEmpty("validCollection", validCollection);

        // then
        assertThat(result).isEqualTo(validCollection);
    }

    @ParameterizedTest
    @MethodSource("io.samituga.slumber.validator.ValidatorDataProvider#valid_ports")
    void should_return_port_when_port_is_valid(int port) {
        // given when
        var result = requiredValidPort(port);

        // then
        assertThat(result).isEqualTo(port);
    }

    @ParameterizedTest
    @MethodSource("io.samituga.slumber.validator.ValidatorDataProvider#invalid_ports")
    void should_fail_port_validation_when_port_is_not_valid(int port) {
        // given
        String expectedMessage = String.format(REQUIRED_VALID_PORT.format(), port);

        // when then
        assertThatThrownBy(() -> requiredValidPort(port))
              .isInstanceOf(AssertionException.class)
              .hasMessage(expectedMessage);
    }

    @Test
    void should_return_params_when_size_is_even() {
        // given
        var params = new String[] {"param1", "param2", "param3", "param4"};

        // when
        var result = requiredArgsPair("params", params);

        // then
        assertThat(result).isEqualTo(params);
    }


    @Test
    void should_throw_exception_if_params_size_is_odd() {
        // given
        var params = new String[] {"param1", "param2", "param3"};

        // when
        String expectedMessage = String.format(REQUIRED_EVEN_VALUES.format(), "params");

        // then
        assertThatThrownBy(() -> requiredArgsPair("params", params))
              .isInstanceOf(AssertionException.class)
              .hasMessage(expectedMessage);
    }

    @Test
    void should_throw_exception_if_params_size_is_empty() {
        // given
        var params = new String[] {};
        String expectedMessage = String.format(REQUIRED_EVEN_VALUES.format(), "params");

        // when then
        assertThatThrownBy(() -> requiredArgsPair("params", params))
              .isInstanceOf(AssertionException.class)
              .hasMessage(expectedMessage);
    }
}
