package org.incode.module.slack.impl;

import com.google.common.base.Strings;

class Util {
    static String coalesce(String... values) {
        for (String value : values) {
            if (!Strings.isNullOrEmpty(value)) {
                return value;
            }

        }
        return null;
    }
}
