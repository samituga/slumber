package io.samituga.bard.endpoint.type;

import io.samituga.slumber.heimer.type.Type;
import java.util.Collections;
import java.util.Map;

public class Headers extends Type<Map<String, String>> {
    private Headers(Map<String, String> value) {
        super(value);
    }

    public static Headers of(Map<String, String> value) {
        return new Headers(Map.copyOf(value));
    }

    public static Headers of(Map.Entry<String, String> value) {
        return new Headers(Map.ofEntries(value));
    }

    public static Headers empty() {
        return new Headers(Collections.emptyMap());
    }
}
