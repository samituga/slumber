package io.samituga.slumber.validator;

/**
 * Message formats for validation exceptions
 */
public enum ValidatorMessageFormat {
    NOT_BLANK("%s is blank");

    private final String format;

    ValidatorMessageFormat(String format) {
        this.format = format;
    }

    public String format() {
        return format;
    }
}
