package io.samituga.slumber.ivern.http.type;

import static io.samituga.slumber.heimer.validator.AssertionUtility.requiredArgsPair;
import static io.samituga.slumber.heimer.validator.AssertionUtility.requiredNotEmpty;

import io.samituga.slumber.ivern.type.Type;
import java.util.HashMap;
import java.util.Map;

public class Headers extends Type<Map<String, String>> {

    private Headers(Map<String, String> headers) {
        super(requiredNotEmpty("headers", headers));
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
            headersMap.put(headers[i], headers[i++]);
        }
        return new Headers(headersMap);
    }
}
