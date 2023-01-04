package io.samituga.bard.endpoint;

public enum HttpCode {
    CONTINUE(100),
    OK(200);

    private final int code;

    HttpCode(int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }

    public boolean isInformational() {
        return false; // TODO: 04/01/2023 Would it be a good addition?
    }
}
