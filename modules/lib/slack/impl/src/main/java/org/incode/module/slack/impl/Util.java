package org.incode.module.slack.impl;

import javax.annotation.Nullable;

class Util {
    static String coalesce(String... values) {
        for (String value : values) {
            if (!isNullOrEmpty(value)) {
                return value;
            }

        }
        return null;
    }

    private static boolean isNullOrEmpty(@Nullable CharSequence x) {
        return x == null || x.length() == 0;
    }

}
