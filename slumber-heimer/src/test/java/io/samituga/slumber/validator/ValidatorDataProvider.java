package io.samituga.slumber.validator;

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

}
