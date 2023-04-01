package io.samituga.slumber.ivern.http.type;

import static io.samituga.slumber.heimer.validator.AssertionUtility.requiredArgsPair;

import io.samituga.slumber.ivern.type.MapType;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

// TODO: 2023-03-26 Support multiple values?
public class Headers extends MapType<String, String> {

    private Headers() {
        super();
    }

    private Headers(Map<String, String> headers) {
        super(headers);
    }

    public static Headers of(String name, String value) {
        return of(Map.entry(name, value));
    }

    public static Headers of(Map.Entry<String, String> header) {
        return of(Map.ofEntries(header));
    }

    public static Headers of(String... headers) {
        return of(mapFromVarargs(headers));
    }

    public static Headers of(Map<String, String> headers) {
        return new Headers(Collections.unmodifiableMap(headers));
    }

    public Headers withHeader(String name, String value) {
        return withHeader(Map.entry(name, value));
    }

    public Headers withHeader(Map.Entry<String, String> header) {
        return withHeaders(Map.ofEntries(header));
    }

    public Headers withHeaders(String... headers) {
        return withHeaders(mapFromVarargs(headers));
    }

    public Headers withHeaders(Map<String, String> headers) {
        Map<String, String> headersMap = new HashMap<>(value());
        headersMap.putAll(headers);
        return of(headersMap);
    }

    public static Headers empty() {
        return new Headers();
    }

    private static Map<String, String> mapFromVarargs(String... headers) {
        requiredArgsPair("headers", headers);
        var headersMap = new HashMap<String, String>();
        for (int i = 0; i < headers.length; i = i + 2) {
            headersMap.put(headers[i], headers[i + 1]);
        }
        return headersMap;
    }
}
