package io.samituga.slumber.heimer.validator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;

public class ValidatorDataProvider {
    static Stream<Arguments> null_and_blank_strings() {
        return Stream.of(
              Arguments.of("ParamName", ""),
              Arguments.of("ParamName", "     "),
              Arguments.of("ParamName", null)
        );
    }

    static Stream<Arguments> valid_strings() {
        return Stream.of(
              Arguments.of("ParamName", "teste"),
              Arguments.of("ParamName", "     asd"),
              Arguments.of("ParamName", "12345"),
              Arguments.of("ParamName", "$#@"),
              Arguments.of("ParamName", "asda    ")
        );
    }

    static Stream<Arguments> null_and_empty_collection() {
        return Stream.of(
              Arguments.of("EmptyList", Collections.emptyList()),
              Arguments.of("Null", null)
        );
    }

    static Stream<Arguments> null_and_empty_and_null_value_map() {

        var nullValueMap = new HashMap<String, String>();
        nullValueMap.put("Key", null);

        return Stream.of(
              Arguments.of("EmptyMap", Map.of()),
              Arguments.of("nullValueMap", nullValueMap),
              Arguments.of("Null", null)
        );
    }

    static Stream<Arguments> valid_ports() {

        return Stream.of(
              Arguments.of(0),
              Arguments.of(1),
              Arguments.of(65534),
              Arguments.of(65535)
        );
    }

    static Stream<Arguments> invalid_ports() {

        return Stream.of(
              Arguments.of(-1),
              Arguments.of(65536)
        );
    }

}
