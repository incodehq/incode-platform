package org.incode.module.base.dom.utils;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class StringUtils {

    private StringUtils() {
    }

    private static Function<String, String> LOWER_CASE_THEN_CAPITALIZE =
            input -> input != null? StringUtils.capitalize(input.toLowerCase()): null;

    private static Function<String, String> UPPER_CASE =
            input -> input != null? input.toUpperCase(): null;

    public static String enumTitle(final String string) {
        if(string == null) {
            return null;
        }
        return splitAndJoin(string, "_", LOWER_CASE_THEN_CAPITALIZE, " ");
    }

    public static String enumDeTitle(final String string) {
        if(string == null) {
            return null;
        }
        return splitAndJoin(string, " ", UPPER_CASE, "_");
    }

    static String splitAndJoin(
            final String string,
            final String splitOn,
            final Function<String, String> mapWith,
            final String joinOn) {
        return String.join(joinOn,
                Arrays.stream(string.split(splitOn))
                        .map(mapWith)
                        .collect(Collectors.toList())
        );
    }

    public static String wildcardToCaseInsensitiveRegex(final String pattern) {
        if(pattern == null) {
            return null;
        }
        return "(?i)".concat(wildcardToRegex(pattern));
    }

    public static String wildcardToRegex(final String pattern) {
        if(pattern == null) {
            return null;
        }
        return pattern.replace("*", ".*").replace("?", ".");
    }

    public static String capitalize(final String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        if (str.length() == 1) {
            return str.toUpperCase();
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }


}
