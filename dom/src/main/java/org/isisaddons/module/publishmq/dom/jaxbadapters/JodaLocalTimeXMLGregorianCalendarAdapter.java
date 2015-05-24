package org.isisaddons.module.publishmq.dom.jaxbadapters;

import javax.xml.datatype.XMLGregorianCalendar;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/**
 * Not registered in the XSD schema (as a JAXB binding, because can only map xs:dateTime once (and have chosen to map to LocalDateTime).
 */
public final class JodaLocalTimeXMLGregorianCalendarAdapter {
    private JodaLocalTimeXMLGregorianCalendarAdapter() {
    }

    private static DateTimeFormatter formatter = ISODateTimeFormat.dateTime();

    public static LocalTime parse(final XMLGregorianCalendar xgc) {
        if(xgc == null) return null;

        final int hour = xgc.getHour();
        final int minute = xgc.getMinute();
        final int second = xgc.getSecond();
        final int millisecond = xgc.getMillisecond();

        return new LocalTime(hour, minute, second, millisecond);
    }

    public static XMLGregorianCalendar print(final LocalTime dateTime) {
        if(dateTime == null) return null;

        final XMLGregorianCalendarImpl xgc = new XMLGregorianCalendarImpl();
        xgc.setHour(dateTime.getHourOfDay());
        xgc.setMinute(dateTime.getMinuteOfHour());
        xgc.setSecond(dateTime.getSecondOfMinute());
        xgc.setMillisecond(dateTime.getMillisOfSecond());

        return xgc;
    }

}
