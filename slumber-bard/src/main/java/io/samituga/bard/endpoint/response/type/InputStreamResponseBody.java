package io.samituga.bard.endpoint.response.type;

import static io.samituga.bard.endpoint.ContentType.TEXT_PLAIN;

import io.samituga.bard.endpoint.ContentType;
import io.samituga.bard.endpoint.response.ResponseBody;
import io.samituga.slumber.heimer.validator.AssertionUtility;
import io.samituga.slumber.ivern.type.Type;
import java.io.InputStream;

public class InputStreamResponseBody extends Type<InputStream> implements ResponseBody {

    public InputStreamResponseBody(InputStream value) {
        super(AssertionUtility.required("value", value));
    }

    public static InputStreamResponseBody of(InputStream value) {
        return new InputStreamResponseBody(value);
    }

    @Override
    public InputStream toInputStream() {
        return value();
    }

    @Override
    public ContentType contentType() {
        return TEXT_PLAIN;
    }

    @Override
    public InputStream responseBody() {
        return value();
    }
}
