package io.samituga.bard.endpoint;

import java.io.InputStream;

public enum ValidResponseTypes {
    BYTE_ARRAY(byte[].class),
    INPUT_STREAM(InputStream.class);


    private final Class<?> type;

    ValidResponseTypes(Class<?> type) {
        this.type = type;
    }

    public Class<?> getType() {
        return type;
    }
}
