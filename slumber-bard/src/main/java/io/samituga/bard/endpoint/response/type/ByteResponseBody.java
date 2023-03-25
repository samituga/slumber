package io.samituga.bard.endpoint.response.type;

import static java.nio.charset.StandardCharsets.UTF_8;

import io.samituga.bard.endpoint.response.ResponseBody;
import io.samituga.slumber.heimer.validator.AssertionUtility;
import io.samituga.slumber.ivern.type.Type;

public class ByteResponseBody extends Type<byte[]> implements ResponseBody {

    public ByteResponseBody(byte[] value) {
        super(AssertionUtility.requiredNotEmpty("value", value));
    }

    public static ByteResponseBody of(byte[] value) {
        return new ByteResponseBody(value);
    }

    public static ByteResponseBody of(String value) {
        return new ByteResponseBody(value.getBytes(UTF_8));
    }

    @Override
    public byte[] responseBody() {
        return value();
    }
}
