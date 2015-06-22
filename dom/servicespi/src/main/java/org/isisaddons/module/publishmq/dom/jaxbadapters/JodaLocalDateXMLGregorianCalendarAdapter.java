package org.isisaddons.module.publishmq.dom.jaxbadapters;

import javax.xml.datatype.XMLGregorianCalendar;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/**
 * Not registered in the XSD schema (as a JAXB binding, because can only map xs:dateTime once (and have chosen to map to LocalDateTime).
 */
public final class JodaLocalDateXMLGregorianCalendarAdapter {
    private JodaLocalDateXMLGregorianCalendarAdapter() {
    }

    private static DateTimeFormatter formatter = ISODateTimeFormat.dateTime();

    public static LocalDate parse(final XMLGregorianCalendar xgc) {
        if(xgc == null) return null;

        final int year = xgc.getYear();
        final int month = xgc.getMonth();
        final int day = xgc.getDay();

        return new LocalDate(year, month, day);
    }

    public static XMLGregorianCalendar print(final LocalDate dateTime) {
        if(dateTime == null) return null;

        final XMLGregorianCalendarImpl xgc = new XMLGregorianCalendarImpl();
        xgc.setYear(dateTime.getYear());
        xgc.setMonth(dateTime.getMonthOfYear());
        xgc.setDay(dateTime.getDayOfMonth());

        return xgc;
    }

}
