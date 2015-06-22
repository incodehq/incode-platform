package org.isisaddons.module.publishmq.dom.jaxbadapters;

import com.google.common.base.Strings;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public final class JodaLocalTimeStringAdapter {
    private JodaLocalTimeStringAdapter() {
    }

    private static DateTimeFormatter dateFormatter = ISODateTimeFormat.localTimeParser();

    public static LocalTime parse(final String date) {
        return !Strings.isNullOrEmpty(date) ? dateFormatter.parseLocalTime(date) : null;
    }

    public static String print(LocalTime date) {
        return date != null? date.toString() : null;
    }

}
