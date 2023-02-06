package io.samituga.slumber.ziggs;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Supplier;

public class WaitFor {

    public static boolean waitFor(Supplier<Boolean> condition) {
        return waitFor(condition, Duration.ofSeconds(1));
    }

    public static boolean waitFor(Supplier<Boolean> condition, Duration timeout) {
        var conditionMet = false;
        var startingTime = Instant.now();
        while (elapsedTime(startingTime).compareTo(timeout) < 0) {
            if (condition.get()) {
                conditionMet = true;
                break;
            }
        }
        return conditionMet;
    }

    private static Duration elapsedTime(Instant start) {
        return Duration.between(start, Instant.now());
    }
}
