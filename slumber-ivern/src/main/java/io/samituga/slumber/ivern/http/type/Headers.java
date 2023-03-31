package io.samituga.slumber.ivern.http.type;

import static io.samituga.slumber.heimer.validator.AssertionUtility.requiredArgsPair;

import io.samituga.slumber.ivern.type.MapType;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Headers extends MapType<String, String> { // TODO: 2023-03-26 Support multiple values?

    private Headers() {
        super();
    }

    private Headers(Map<String, String> headers) {
        super(headers);
    }

    public static Headers of(String name, String value) {
        return new Headers(Map.of(name, value));
    }

    public static Headers of(Map<String, String> headers) {
        return new Headers(headers);
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
        return new Headers();
    }
}
