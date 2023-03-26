package io.samituga.bard.endpoint.request.type;

import static java.nio.charset.StandardCharsets.UTF_8;

import io.samituga.slumber.heimer.validator.AssertionUtility;
import io.samituga.slumber.ivern.type.Type;

public class RequestBody extends Type<byte[]> {

    public RequestBody(byte[] value) {
        super(AssertionUtility.requiredNotEmpty("value", value));
    }

    public static RequestBody of(byte[] value) {
        return new RequestBody(value);
    }

    public static RequestBody of(String value) {
        return new RequestBody(value.getBytes(UTF_8));
    }
}
