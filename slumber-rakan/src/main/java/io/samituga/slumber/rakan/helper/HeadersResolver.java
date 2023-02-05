package io.samituga.slumber.rakan.helper;

import io.samituga.slumber.ivern.http.type.Headers;
import java.util.concurrent.atomic.AtomicInteger;

public final class HeadersResolver {

    public static String[] from(Headers headers) {
        var index = new AtomicInteger(0);
        var headersArray = new String[headers.value().size() * 2];
        headers.value().forEach((key, value) -> {
            headersArray[index.get()] = key;
            headersArray[index.get() + 1] = value;
            index.set(index.get() + 2);
        });
        return headersArray;
    }
}
