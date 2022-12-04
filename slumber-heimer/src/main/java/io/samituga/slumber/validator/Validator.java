package io.samituga.slumber.validator;

import static io.samituga.slumber.error.UtilityClassInstantiationError.MESSAGE_FORMAT;
import static io.samituga.slumber.validator.ValidatorMessageFormat.NOT_BLANK;

import io.samituga.slumber.error.UtilityClassInstantiationError;
import io.samituga.slumber.exception.ValidationException;
import java.util.function.Supplier;

/**
 * Utility class that performs validations on inputs and throws {@link ValidationException}
 * if the validation fails.
 */
public final class Validator {

    private Validator() {
        throw new UtilityClassInstantiationError(
              String.format(MESSAGE_FORMAT, this.getClass().getSimpleName()));
    }

    /**
     * Validates if string input is not null, not empty and not blank.
     *
     * @param name  the name of the parameter.
     * @param value the value of the parameter.
     * @throws ValidationException if the value fails the validation.
     */
    public static void notBlank(String name, String value) {
        validate(() -> value == null || value.isBlank(), NOT_BLANK, name);
    }

    private static void validate(Supplier<Boolean> validation, ValidatorMessageFormat messageFormat,
                                 Object... params) {
        validate(validation, messageFormat.format(), params);
    }

    private static void validate(Supplier<Boolean> validation, String format, Object... params) {
        if (validation.get()) {
            throw new ValidationException(String.format(format, params));
        }
    }
}
