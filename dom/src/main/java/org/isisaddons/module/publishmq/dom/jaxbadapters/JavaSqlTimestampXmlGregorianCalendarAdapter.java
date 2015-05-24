package org.isisaddons.module.publishmq.dom.jaxbadapters;

import java.sql.Timestamp;
import java.util.GregorianCalendar;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class JavaSqlTimestampXmlGregorianCalendarAdapter extends XmlAdapter<XMLGregorianCalendar, java.sql.Timestamp> {


    // this assumes DTF is thread-safe, which it most probably is..
    // http://bugs.java.com/bugdatabase/view_bug.do?bug_id=6466177
    static DatatypeFactory datatypeFactory = null;

    private static DatatypeFactory getDatatypeFactory() {
        if(datatypeFactory == null) {
            try {
                datatypeFactory = DatatypeFactory.newInstance();
                return datatypeFactory;
            } catch (DatatypeConfigurationException e) {
                throw new RuntimeException(e);
            }
        }
        return datatypeFactory;
    }

    public static java.sql.Timestamp parse(final XMLGregorianCalendar calendar) {
        return calendar != null
                ? new Timestamp(calendar.toGregorianCalendar().getTime().getTime())
                : null;
    }

    public static XMLGregorianCalendar print(final java.sql.Timestamp timestamp) {
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(timestamp);
        return getDatatypeFactory().newXMLGregorianCalendar(c);
    }

    @Override
    public Timestamp unmarshal(final XMLGregorianCalendar v) throws Exception {
        return null;
    }

    @Override
    public XMLGregorianCalendar marshal(final Timestamp v) throws Exception {
        return null;
    }
}
