package io.samituga.slumber.bard.javalin.mapper;

import static io.samituga.bard.endpoint.Verb.PATH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.javalin.http.HandlerType;
import io.samituga.bard.endpoint.Verb;
import io.samituga.slumber.bard.javalin.exception.UnsupportedVerbException;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class VerbToHandlerTypeTest {


    static Stream<Arguments> verb_to_match_handler_type() {
        return Stream.of(
              Arguments.of(Verb.GET, HandlerType.GET),
              Arguments.of(Verb.HEAD, HandlerType.HEAD),
              Arguments.of(Verb.POST, HandlerType.POST),
              Arguments.of(Verb.PUT, HandlerType.PUT),
              Arguments.of(Verb.DELETE, HandlerType.DELETE),
              Arguments.of(Verb.CONNECT, HandlerType.CONNECT),
              Arguments.of(Verb.OPTIONS, HandlerType.OPTIONS),
              Arguments.of(Verb.TRACE, HandlerType.TRACE)
        );
    }

    @ParameterizedTest
    @MethodSource("verb_to_match_handler_type")
    void should_map_verb_to_handler_type(Verb given, HandlerType expected) {
        // given when
        var result = VerbToHandlerType.toHandlerType(given);
        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void should_throw_exception_when_using_unsupported_verb() {
        // given when then
        assertThatThrownBy(() -> VerbToHandlerType.toHandlerType(PATH))
              .isInstanceOf(UnsupportedVerbException.class);
    }
}
