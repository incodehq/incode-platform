package org.isisaddons.module.publishmq.dom.jaxbadapters;

import com.google.common.base.Strings;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/**
 * Not registered in the XSD schema (as a JAXB binding, because can only map xs:dateTime once (and have chosen to map to LocalDateTime).
 */
public final class JodaDateTimeStringAdapter {
    private JodaDateTimeStringAdapter() {
    }

    private static DateTimeFormatter formatter = ISODateTimeFormat.dateTime();

    public static DateTime parse(final String date) {
        return !Strings.isNullOrEmpty(date) ? formatter.parseDateTime(date) : null;
    }

    public static String print(final DateTime date) {
        return date != null? date.toString(): null;
    }

}
