package io.samituga.bard.endpoint.request.type;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.samituga.jayce.Json;
import io.samituga.jayce.JsonException;
import io.samituga.slumber.heimer.validator.AssertionUtility;
import io.samituga.slumber.ivern.type.Type;

public class RequestAsTypeBody<T> extends Type<T> {

    public RequestAsTypeBody(T value) {
        super(AssertionUtility.required("value", value));
    }

    public static <T> RequestAsTypeBody<T> of(byte[] value, Class<T> type) {
        return of(new String(value, UTF_8), type);
    }

    public static <T> RequestAsTypeBody<T> of(String value, Class<T> type) {
        try {
            return new RequestAsTypeBody<>(Json.mapper().readValue(value, type));
        } catch (JsonProcessingException e) {
            throw new JsonException(e);
        }
    }
}
