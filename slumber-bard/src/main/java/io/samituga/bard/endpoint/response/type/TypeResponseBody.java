package io.samituga.bard.endpoint.response.type;

import static io.samituga.bard.endpoint.ContentType.APPLICATION_JSON;
import static io.samituga.bard.endpoint.ContentType.TEXT_PLAIN;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.samituga.bard.endpoint.ContentType;
import io.samituga.bard.endpoint.response.ResponseBody;
import io.samituga.jayce.Json;
import io.samituga.jayce.JsonException;
import io.samituga.slumber.heimer.validator.AssertionUtility;
import io.samituga.slumber.ivern.type.Type;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class TypeResponseBody<T> extends Type<T> implements ResponseBody {
    public TypeResponseBody(T value) {
        super(AssertionUtility.required("value", value));
    }

    public static <T> TypeResponseBody<T> of(T value) {
        return new TypeResponseBody<>(value);
    }

    @Override
    public InputStream toInputStream() {
        try {
            var bytes = Json.mapper().writeValueAsBytes(value());
            return new ByteArrayInputStream(bytes);
        } catch (JsonProcessingException e) {
            throw new JsonException(e);
        }
    }

    @Override
    public ContentType contentType() {
        return APPLICATION_JSON;
    }

    @Override
    public T responseBody() {
        return value();
    }
}
