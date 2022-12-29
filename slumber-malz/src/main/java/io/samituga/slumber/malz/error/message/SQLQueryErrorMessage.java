package io.samituga.slumber.malz.error.message;

public enum SQLQueryErrorMessage {
    FIND_ONE_ERROR("Expected 1 record but received multiple. Condition %s");


    private final String format;

    SQLQueryErrorMessage(String format) {this.format = format;}

    public String format() {
        return format;
    }
}
