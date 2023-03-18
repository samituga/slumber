package io.samituga.slumber.ziggs;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.function.Supplier;

public class WaitFor {

    public static boolean waitFor(Supplier<Boolean> condition) {
        return waitFor(condition, Duration.ofSeconds(1));
    }

    public static boolean waitFor(Supplier<Boolean> condition, Duration timeout) {
        return waitFor(condition, timeout, Clock.systemDefaultZone());
    }

    public static boolean waitFor(Supplier<Boolean> condition, Duration timeout, Clock clock) {
        var conditionMet = false;
        var startingTime = clock.instant();
        while (elapsedTime(startingTime, clock).compareTo(timeout) < 0) {
            if (condition.get()) {
                conditionMet = true;
                break;
            }
        }
        return conditionMet;
    }

    private static Duration elapsedTime(Instant start, Clock clock) {
        return Duration.between(start, clock.instant());
    }
}
