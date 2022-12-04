package io.samituga.slumber.validator;

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
              () -> Validator.notBlank(paramName, value));

        String expectedMessage = String.format(NOT_BLANK.format(), paramName);
        assertEquals(expectedMessage, exception.getMessage());
    }
}
