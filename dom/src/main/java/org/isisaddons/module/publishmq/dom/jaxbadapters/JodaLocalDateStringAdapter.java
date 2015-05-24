package org.isisaddons.module.publishmq.dom.jaxbadapters;

import com.google.common.base.Strings;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public final class JodaLocalDateStringAdapter {
    private JodaLocalDateStringAdapter() {
    }

    private static DateTimeFormatter dateFormatter = ISODateTimeFormat.localDateParser();

    public static LocalDate parse(final String date) {
        return !Strings.isNullOrEmpty(date) ? dateFormatter.parseLocalDate(date) : null;
    }

    public static String print(LocalDate date) {
        return date != null? date.toString() : null;
    }

}
