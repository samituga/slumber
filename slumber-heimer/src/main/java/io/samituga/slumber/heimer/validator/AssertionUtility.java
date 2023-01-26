package io.samituga.slumber.heimer.validator;

import static io.samituga.slumber.heimer.error.UtilityClassInstantiationError.MESSAGE_FORMAT;
import static io.samituga.slumber.heimer.validator.ValidatorMessageFormat.NOT_BLANK;
import static io.samituga.slumber.heimer.validator.ValidatorMessageFormat.REQUIRED;
import static io.samituga.slumber.heimer.validator.ValidatorMessageFormat.REQUIRED_EVEN_VALUES;
import static io.samituga.slumber.heimer.validator.ValidatorMessageFormat.REQUIRED_NOT_EMPTY;
import static io.samituga.slumber.heimer.validator.ValidatorMessageFormat.REQUIRED_VALID_PORT;

import io.samituga.slumber.heimer.error.UtilityClassInstantiationError;
import io.samituga.slumber.heimer.exception.AssertionException;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Utility class that performs validations on inputs and throws {@link AssertionException}
 * if the validation fails.
 */
public final class AssertionUtility {

    private AssertionUtility() {
        throw new UtilityClassInstantiationError(
              String.format(MESSAGE_FORMAT, this.getClass().getSimpleName()));
    }

    // TODO: 2022-12-05 Also receive execution location?

    /**
     * Validates if string input is not null, not empty and not blank.
     *
     * @param name  the name of the parameter
     * @param value the value of the parameter
     * @return the value if passes validation
     * @throws AssertionException if the value fails the validation
     */
    public static String requiredNotBlank(String name, String value) {
        validate(() -> value == null || value.isBlank(), NOT_BLANK, name);
        return value;
    }

    /**
     * Validates if input is not null.
     *
     * @param name  the name of the parameter
     * @param value the value of the parameter
     * @return the value if passes validation
     * @throws AssertionException if the value fails the validation
     */
    public static <T> T required(String name, T value) {
        validate(() -> value == null, REQUIRED, name);
        return value;
    }

    /**
     * Validates if collection input is not null and not empty.
     *
     * @param name  the name of the parameter
     * @param value the value of the parameter
     * @return the value if passes validation
     * @throws AssertionException if the value fails the validation
     */
    public static <T> Collection<T> requiredNotEmpty(String name, Collection<T> value) {
        validate(() -> value == null || value.isEmpty(), REQUIRED_NOT_EMPTY,
              name);
        return value;
    }

    /**
     * Validates if map input is not null and not empty and values are not null.
     *
     * @param name  the name of the parameter
     * @param value the value of the parameter
     * @return the value if passes validation
     * @throws AssertionException if the value fails the validation
     */
    public static <K, V> Map<K, V> requiredNotEmpty(String name, Map<K, V> value) {
        validate(() -> value == null
                    || value.isEmpty()
                    || value.values().stream().anyMatch(Objects::isNull),
              REQUIRED_NOT_EMPTY, name);
        return value;
    }

    /**
     * Validates if port input is between 0 and 65535 inclusive.
     *
     * @param port the port number
     * @return the port number if it passes the validation
     * @throws AssertionException if the value fails the validation
     */
    public static int requiredValidPort(int port) {
        var min = 0;
        var max = 65535;
        validate(() -> port < min || port > max, REQUIRED_VALID_PORT, port);
        return port;
    }

    /**
     * Validates if number of parameters are even and not 0.
     *
     * @param name the name of the parameter
     * @param params the params
     * @return The params as array if passes the validation
     * @param <T> The type of the params
     */
    public static <T> T[] requiredArgsPair(String name, T... params) {
        validate(() -> params.length == 0
                    || params.length % 2 != 0,
              REQUIRED_EVEN_VALUES, name);
        return params;
    }

    public static void validate(Supplier<Boolean> validation, ValidatorMessageFormat messageFormat,
                                Object... params) {
        validate(validation, messageFormat.format(), params);
    }

    public static void validate(Supplier<Boolean> validation, String format, Object... params) {
        if (validation.get()) {
            throw new AssertionException(String.format(format, params));
        }
    }
}
