package io.samituga.bard.endpoint.response.type;

import static io.samituga.bard.endpoint.ContentType.TEXT_PLAIN;
import static java.nio.charset.StandardCharsets.UTF_8;

import io.samituga.bard.endpoint.ContentType;
import io.samituga.bard.endpoint.response.ResponseBody;
import io.samituga.slumber.heimer.validator.AssertionUtility;
import io.samituga.slumber.ivern.type.Type;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

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

    public InputStream toInputStream() {
        return new ByteArrayInputStream(value());
    }

    @Override
    public ContentType contentType() {
        return TEXT_PLAIN;
    }

    @Override
    public byte[] responseBody() {
        return value();
    }
}
