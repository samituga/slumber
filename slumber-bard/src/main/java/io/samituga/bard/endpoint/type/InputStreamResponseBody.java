package io.samituga.bard.endpoint.type;

import io.samituga.bard.endpoint.ResponseBody;
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
    public InputStream responseBody() {
        return value();
    }
}
