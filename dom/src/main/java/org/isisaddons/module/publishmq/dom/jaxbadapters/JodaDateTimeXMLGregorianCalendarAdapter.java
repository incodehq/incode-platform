package org.isisaddons.module.publishmq.dom.jaxbadapters;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.xml.datatype.XMLGregorianCalendar;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/**
 * Not registered in the XSD schema (as a JAXB binding, because can only map xs:dateTime once (and have chosen to map to LocalDateTime).
 */
public final class JodaDateTimeXMLGregorianCalendarAdapter {
    private JodaDateTimeXMLGregorianCalendarAdapter() {
    }

    private static DateTimeFormatter formatter = ISODateTimeFormat.dateTime();

    public static DateTime parse(final XMLGregorianCalendar xgc) {
        if(xgc == null) return null;

        final GregorianCalendar gc = xgc.toGregorianCalendar();
        final Date time = gc.getTime();
        final TimeZone timeZone = gc.getTimeZone();

        final DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(timeZone);
        return new DateTime(time, dateTimeZone);
    }

    public static XMLGregorianCalendar print(final DateTime dateTime) {
        if(dateTime == null) return null;

        final long millis = dateTime.getMillis();
        final DateTimeZone dateTimeZone = dateTime.getZone();

        final TimeZone timeZone = dateTimeZone.toTimeZone();
        final GregorianCalendar calendar = new GregorianCalendar(timeZone);
        calendar.setTimeInMillis(millis);

        return new XMLGregorianCalendarImpl(calendar);
    }

}
