package io.samituga.slumber.heimer.util;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class UrlQueryUtils {

    public static Map<String, List<String>> parseQueryParams(String query) {
        if (query.isEmpty()) {
            return Collections.emptyMap();
        }

        return Arrays.stream(query.split("&"))
              .map(s -> s.split("=", 2))
              .collect(Collectors.groupingBy(
                    s -> decodeUrlComponent(s[0]),
                    Collectors.mapping(
                          s -> s.length > 1 ? decodeUrlComponent(s[1]) : null,
                          Collectors.toList())))
              .entrySet().stream()
              .collect(Collectors.toUnmodifiableMap(
                    Map.Entry::getKey,
                    e -> e.getValue().stream()
                          .filter(Objects::nonNull)
                          .collect(Collectors.toList())));
    }

    private static String decodeUrlComponent(String s) {
        try {
            return URLDecoder.decode(s, UTF_8);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid URL encoding: " + s, e);
        }
    }
}
