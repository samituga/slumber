package io.samituga.slumber.heimer.util;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class UrlQueryUtils {

    public static Map<String, Set<String>> parseQueryParams(String query) {
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
                          .collect(Collectors.toSet())));
    }

    public static String createQueryString(Map<String, Set<String>> queryMap) {
        var sb = new StringBuilder();

        queryMap.forEach((key, value1) -> {
            value1.forEach(value -> {
                if (sb.length() > 0) {
                    sb.append("&");
                }
                sb.append(URLEncoder.encode(key, UTF_8));
                sb.append("=");
                sb.append(URLEncoder.encode(value, UTF_8));
            });
        });

        return sb.toString();
    }


    private static String decodeUrlComponent(String s) {
        try {
            return URLDecoder.decode(s, UTF_8);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid URL encoding: " + s, e);
        }
    }
}
