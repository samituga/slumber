package io.samituga.slumber.heimer.util;

import static io.samituga.slumber.heimer.error.UtilityClassInstantiationError.UTILITY_CLASS_ERROR_FORMAT;

import io.samituga.slumber.heimer.error.UtilityClassInstantiationError;

public final class StringUtils {

    private StringUtils() {
        throw new UtilityClassInstantiationError(
              String.format(UTILITY_CLASS_ERROR_FORMAT, this.getClass().getSimpleName()));
    }

    public static boolean isEnclosedBy(String target, String prefix, String suffix) {
        return target.startsWith(prefix) && target.endsWith(suffix);
    }

    public static String stripEnclosing(String target, String prefix, String suffix) {
        var targetWithoutPrefix = stripPrefix(target, prefix);
        return stripSuffix(targetWithoutPrefix, suffix);
    }

    public static String stripPrefix(String target, String prefix) {
        if (target.startsWith(prefix)) {
            return target.substring(prefix.length());
        }
        return target;
    }

    public static String stripSuffix(String target, String suffix) {
        if (target.endsWith(suffix)) {
            return target.substring(0, target.length() - suffix.length());
        }
        return target;
    }
}
