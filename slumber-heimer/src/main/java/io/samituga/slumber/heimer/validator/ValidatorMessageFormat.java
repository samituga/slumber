package io.samituga.slumber.heimer.validator;

/**
 * Message formats for validation exceptions
 */
public enum ValidatorMessageFormat {
    NOT_BLANK("%s is blank"),
    REQUIRED("%s is required");

    private final String format;

    ValidatorMessageFormat(String format) {
        this.format = format;
    }

    public String format() {
        return format;
    }
}
