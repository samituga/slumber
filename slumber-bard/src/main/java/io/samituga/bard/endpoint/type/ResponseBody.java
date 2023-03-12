package io.samituga.bard.endpoint.type;

import static java.nio.charset.StandardCharsets.UTF_8;

import io.samituga.slumber.heimer.validator.AssertionUtility;
import io.samituga.slumber.ivern.type.Type;

public class ResponseBody extends Type<byte[]> {
    public ResponseBody(byte[] value) {
        super(AssertionUtility.requiredNotEmpty("value", value));
    }

    public static ResponseBody of(byte[] value) {
        return new ResponseBody(value);
    }

    public static ResponseBody of(String value) {
        return new ResponseBody(value.getBytes(UTF_8));
    }
}
