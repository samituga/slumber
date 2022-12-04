package io.samituga.slumber.validator;

import static io.samituga.slumber.validator.Validator.notBlank;
import static io.samituga.slumber.validator.ValidatorMessageFormat.NOT_BLANK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.samituga.slumber.exception.ValidationException;
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
}
