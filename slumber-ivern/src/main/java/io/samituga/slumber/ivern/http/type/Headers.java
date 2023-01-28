package io.samituga.slumber.ivern.http.type;

import static io.samituga.slumber.heimer.validator.AssertionUtility.required;
import static io.samituga.slumber.heimer.validator.AssertionUtility.requiredArgsPair;

import io.samituga.slumber.ivern.type.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Headers extends Type<Map<String, String>> {

    private Headers(Map<String, String> headers) {
        super(required("headers", headers));
    }


    public int size() {
        return value().size();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public static Headers of(String name, String value) {
        return new Headers(Map.of(name, value));
    }

    public static Headers of(Map<String, String> headers) {
        return new Headers(Map.copyOf(headers));
    }

    public static Headers of(Map.Entry<String, String> headers) {
        return new Headers(Map.ofEntries(headers));
    }

    public static Headers of(String... headers) {
        requiredArgsPair("headers", headers);
        var headersMap = new HashMap<String, String>();
        for (int i = 0; i < headers.length; i = i + 2) {
            headersMap.put(headers[i], headers[i + 1]);
        }
        return new Headers(Collections.unmodifiableMap(headersMap));
    }

    public static Headers empty() {
        return new Headers(Collections.emptyMap());
    }
}
