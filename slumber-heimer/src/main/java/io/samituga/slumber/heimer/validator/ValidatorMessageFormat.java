package io.samituga.slumber.heimer.validator;

/**
 * Message formats for validation exceptions
 */
public enum ValidatorMessageFormat {
    NOT_BLANK("%s is blank"),
    REQUIRED("%s is required"),
    REQUIRED_NOT_EMPTY("%s is required and can't be empty"),
    REQUIRED_VALID_PORT("Port number %d is not valid"),
    REQUIRED_EVEN_VALUES("%s length must be even"),
    ;

    private final String format;

    ValidatorMessageFormat(String format) {
        this.format = format;
    }

    public String format() {
        return format;
    }
}
