package io.samituga.slumber.ziggs;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

class WaitForTest {

    private static final Duration THRESHOLD = Duration.ofMillis(5);
    private static final Duration TIMEOUT = Duration.ofMillis(50);

    @Test
    void should_return_true_when_condition_is_true() {
        // given
        var now = Instant.now();
        Supplier<Boolean> action =
              () -> Duration.between(now, Instant.now()).compareTo(TIMEOUT) > 0;

        // when
        var result = WaitFor.waitFor(action, TIMEOUT.plus(THRESHOLD));

        // then
        assertThat(result).isTrue();
    }

    @Test
    void should_return_false_when_timeout_is_reached() {
        // given
        var now = Instant.now();
        Supplier<Boolean> action =
              () -> Duration.between(now, Instant.now()).compareTo(TIMEOUT.plus(THRESHOLD)) > 0;

        // when
        var result = WaitFor.waitFor(action, TIMEOUT);

        // then
        assertThat(result).isFalse();
    }

    @Test
    void elapsed_time_should_be_similar_to_timeout() {
        // given
        var now = Instant.now();
        Supplier<Boolean> action =
              () -> Duration.between(now, Instant.now()).compareTo(TIMEOUT) > 0;

        // when
        var result = WaitFor.waitFor(action, TIMEOUT.plus(THRESHOLD));
        var elapsedTime = Duration.between(now, Instant.now());
        var elapsedTimeMinusTimeout = elapsedTime.minus(TIMEOUT);

        // then
        assertThat(result).isTrue();
        assertThat(elapsedTimeMinusTimeout).isLessThan(THRESHOLD);
    }
}