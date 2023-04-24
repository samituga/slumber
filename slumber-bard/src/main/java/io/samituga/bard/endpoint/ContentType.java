package io.samituga.bard.endpoint;

public enum ContentType {
    TEXT_HTML("text/html"),
    TEXT_PLAIN("text/plain"),
    APPLICATION_JSON("application/json"),
    APPLICATION_XML("application/xml"),
    APPLICATION_X_WWW_FORM_URLENCODED("application/x-www-form-urlencoded"),
    IMAGE_PNG("image/png"),
    IMAGE_JPEG("image/jpeg"),
    MULTIPART_FORM_DATA("multipart/form-data"),
    ;

    private final String value;

    ContentType(String contentType) {
        this.value = contentType;
    }

    public String value() {
        return value;
    }
}
