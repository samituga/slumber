package io.samituga.slumber.rakan.helper;

import static org.assertj.core.api.Assertions.assertThat;

import io.samituga.slumber.ivern.http.type.Headers;
import org.junit.jupiter.api.Test;

class HeadersResolverTest {


    @Test
    void should_convert_headers_to_string_array_maintaining_correct_order() {
        var key1 = "key1";
        var value1 = "value1";
        var key2 = "key2";
        var value2 = "value2";
        var headers = Headers.of(key1, value1, key2, value2);


        var result = HeadersResolver.from(headers);


        var expected = new String[] {key1, value1, key2, value2};
        assertThat(result).containsExactly(expected);
    }
}