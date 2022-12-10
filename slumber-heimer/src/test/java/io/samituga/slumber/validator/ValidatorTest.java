package io.samituga.slumber.validator;

import static io.samituga.slumber.heimer.validator.Validator.notBlank;
import static io.samituga.slumber.heimer.validator.Validator.required;
import static io.samituga.slumber.heimer.validator.ValidatorMessageFormat.NOT_BLANK;
import static io.samituga.slumber.heimer.validator.ValidatorMessageFormat.REQUIRED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.samituga.slumber.heimer.exception.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class ValidatorTest {


    @ParameterizedTest
    @MethodSource("io.samituga.slumber.validator.ValidatorDataProvider#null_and_blank_strings")
    void should_fail_not_empty_validation_when_strings_are_invalid(String paramName, String value) {
        var exception = assertThrows(ValidationException.class,
              () -> notBlank(paramName, value));

        String expectedMessage = String.format(NOT_BLANK.format(), paramName);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("io.samituga.slumber.validator.ValidatorDataProvider#valid_strings")
    void should_return_value_when_strings_are_valid(String paramName, String value) {
        String result = notBlank(paramName, value);

        assertEquals(value, result);
    }

    @Test
    void should_fail_required_validation_when_value_is_null() {
        var paramName = "paramName";
        var exception = assertThrows(ValidationException.class,
              () -> required(paramName, null));

        String expectedMessage = String.format(REQUIRED.format(), paramName);
        assertEquals(expectedMessage, exception.getMessage());
    }


    @Test
    void should_return_value_when_required_is_valid() {
        var paramName = "paramName";
        var value = "value";
        String result = required(paramName, value);

        assertEquals(value, result);
    }
}
