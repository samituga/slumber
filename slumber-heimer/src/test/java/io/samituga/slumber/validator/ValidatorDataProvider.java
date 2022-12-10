package io.samituga.slumber.validator;

import java.util.Collections;
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

}
