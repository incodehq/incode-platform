package org.isisaddons.module.publishmq.dom.jaxbadapters;

import com.google.common.base.Strings;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public final class JodaLocalDateTimeStringAdapter {
    private JodaLocalDateTimeStringAdapter() {
    }

    private static DateTimeFormatter formatter = ISODateTimeFormat.localDateOptionalTimeParser();

    public static LocalDateTime parse(final String date) {
        return !Strings.isNullOrEmpty(date) ? formatter.parseLocalDateTime(date) : null;
    }

    public static String print(final LocalDateTime date) {
        return date != null? date.toString(): null;
    }

}
