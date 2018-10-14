package org.incode.module.base.dom.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

public final class MessageUtils {

    private MessageUtils(){}
    private final static Pattern pattern = Pattern.compile(".*Reason: (.+?)[ ]*Identifier:.*");

    public static String normalize(final Exception ex) {
        if(ex == null || isNullOrEmpty(ex.getMessage())) {
            return null;
        }
        String message = ex.getMessage();
        final Matcher matcher = pattern.matcher(message);
        if(matcher.matches()) {
            return matcher.group(1);
        }
        return message;
    }

    private static boolean isNullOrEmpty(@Nullable CharSequence x) {
        return x == null || x.length() == 0;
    }

}
