package org.isisaddons.module.fakedata.dom;

import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.services.clock.ClockService;
import org.apache.isis.applib.value.Blob;
import org.apache.isis.applib.value.Clob;
import org.apache.isis.applib.value.Money;
import org.apache.isis.applib.value.Password;
import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2;

public class FakeDataServiceTest {

    @Rule
    public JUnitRuleMockery2 context = JUnitRuleMockery2.createFor(JUnitRuleMockery2.Mode.INTERFACES_AND_CLASSES);

    FakeDataService fakeDataService;

    @Mock
    DomainObjectContainer mockContainer;

    @Mock
    ClockService mockClockService;

    @Before
    public void setUp() throws Exception {
        fakeDataService = new FakeDataService();
        fakeDataService.container = mockContainer;
        fakeDataService.clockService = mockClockService;
        fakeDataService.init();

        final DateTime now = new DateTime();
        final LocalDate nowAsLocalDate = now.toLocalDate();
        final long nowAsMillis = now.toDate().getTime();
        final LocalDateTime nowAsLocalDateTime = now.toLocalDateTime();
        final Timestamp nowAsJavaSqlTimestamp = new Timestamp(now.toDate().getTime());

        context.checking(new Expectations() {{
            allowing(mockClockService).nowAsDateTime();
            will(returnValue(now));

            allowing(mockClockService).now();
            will(returnValue(nowAsLocalDate));

            allowing(mockClockService).nowAsMillis();
            will(returnValue(nowAsMillis));

            allowing(mockClockService).nowAsLocalDateTime();
            will(returnValue(nowAsLocalDateTime));

            allowing(mockClockService).nowAsJavaSqlTimestamp();
            will(returnValue(nowAsJavaSqlTimestamp));
        }});
    }

    public static class IsisBlobsTest extends FakeDataServiceTest {

        IsisBlobs isisBlobs;

        @Before
        public void setUp() throws Exception {
            super.setUp();
            isisBlobs = fakeDataService.isisBlobs();
        }

        @Test
        public void any() throws Exception {

            final Blob blob = isisBlobs.any();

            Assertions.assertThat(blob).isNotNull();
            Assertions.assertThat(blob.getName()).isNotNull();
            Assertions.assertThat(blob.getBytes()).isNotNull();
            Assertions.assertThat(blob.getBytes().length).isGreaterThan(0);
            Assertions.assertThat(blob.getMimeType()).isNotNull();
        }

        @Test
        public void anyJpg() throws Exception {

            final Blob blob = isisBlobs.anyJpg();

            Assertions.assertThat(blob).isNotNull();
            Assertions.assertThat(blob.getName()).endsWith(".jpg");
            Assertions.assertThat(blob.getBytes()).isNotNull();
            Assertions.assertThat(blob.getBytes().length).isGreaterThan(0);
            Assertions.assertThat(blob.getMimeType().toString()).isEqualTo("image/jpeg");
        }


        @Test
        public void anyPdf() throws Exception {

            final Blob blob = isisBlobs.anyPdf();

            Assertions.assertThat(blob).isNotNull();
            Assertions.assertThat(blob.getName()).endsWith(".pdf");
            Assertions.assertThat(blob.getBytes()).isNotNull();
            Assertions.assertThat(blob.getBytes().length).isGreaterThan(0);
            Assertions.assertThat(blob.getMimeType().toString()).isEqualTo("application/pdf");
        }

    }

    public static class IsisClobsTest extends FakeDataServiceTest {

        IsisClobs isisClobs;

        @Before
        public void setUp() throws Exception {
            super.setUp();
            isisClobs = fakeDataService.isisClobs();
        }

        @Test
        public void any() throws Exception {

            final Clob clob = isisClobs.any();

            Assertions.assertThat(clob).isNotNull();
            Assertions.assertThat(clob.getName()).isNotNull();
            Assertions.assertThat(clob.getChars()).isNotNull();
            Assertions.assertThat(clob.getChars().length()).isGreaterThan(0);
            Assertions.assertThat(clob.getMimeType()).isNotNull();
        }

        @Test
        public void anyRtf() throws Exception {

            final Clob clob = isisClobs.anyRtf();

            Assertions.assertThat(clob).isNotNull();
            Assertions.assertThat(clob.getName()).endsWith(".rtf");
            Assertions.assertThat(clob.getChars()).isNotNull();
            Assertions.assertThat(clob.getChars().length()).isGreaterThan(0);
            Assertions.assertThat(clob.getMimeType().toString()).isEqualTo("application/rtf");
        }


        @Test
        public void anyXml() throws Exception {

            final Clob clob = isisClobs.anyXml();

            Assertions.assertThat(clob).isNotNull();
            Assertions.assertThat(clob.getName()).endsWith(".xml");
            Assertions.assertThat(clob.getChars()).isNotNull();
            Assertions.assertThat(clob.getChars().length()).isGreaterThan(0);
            Assertions.assertThat(clob.getMimeType().toString()).isEqualTo("text/xml");
        }

    }

    @Test
    public void bytes_upTo() throws Exception {
        final byte b = fakeDataService.bytes().upTo((byte) 10);
        Assertions.assertThat(b).isLessThan((byte)10);
    }

    @Test
    public void shorts_upTo() throws Exception {
        final short s = fakeDataService.shorts().upTo((short) 10);
        Assertions.assertThat(s).isLessThan((short)10);
    }

    @Test
    public void ints_upTo() throws Exception {
        final int i = fakeDataService.ints().upTo(10);
        Assertions.assertThat(i).isLessThan(10);
    }

    @Test
    public void strings_fixed() throws Exception {
        final String str = fakeDataService.strings().fixed(12);
        Assertions.assertThat(str.length()).isEqualTo(12);
    }

    @Test
    public void strings_upper() throws Exception {
        final String str = fakeDataService.strings().upper(8);
        Assertions.assertThat(str.length()).isEqualTo(8);
        Assertions.assertThat(str).matches("[A-Z]{8}");
    }

    @Test
    public void passwords_any() throws Exception {
        final Password pwd = fakeDataService.isisPasswords().any();
        Assertions.assertThat(pwd.getPassword()).isNotNull();
        Assertions.assertThat(pwd.getPassword().length()).isEqualTo(12);
    }

    @Test
    public void moneys_any() throws Exception {
        final Money pwd = fakeDataService.isisMoneys().any();
        Assertions.assertThat(pwd.getAmount()).isNotNull();
        Assertions.assertThat(pwd.getCurrency()).isNotNull();
    }

    @Test
    public void jodaDateTimes_any() throws Exception {
        final DateTime any = fakeDataService.jodaDateTimes().any();
        Assertions.assertThat(any).isNotNull();
    }

    @Test
    public void jodaLocalDates_any() throws Exception {
        final LocalDate any = fakeDataService.jodaLocalDates().any();
        Assertions.assertThat(any).isNotNull();
    }

    @Test
    public void javaUtilDates_any() throws Exception {
        final Date any = fakeDataService.javaUtilDates().any();
        Assertions.assertThat(any).isNotNull();
    }

    @Test
    public void javaSqlDates_any() throws Exception {
        final java.sql.Date any = fakeDataService.javaSqlDates().any();
        Assertions.assertThat(any).isNotNull();
    }

    @Test
    public void javaSqlTimestamps_any() throws Exception {
        final Timestamp any = fakeDataService.javaSqlTimestamps().any();
        Assertions.assertThat(any).isNotNull();
    }

    @Test
    public void urls_any() throws Exception {
        final URL any = fakeDataService.urls().any();
        Assertions.assertThat(any).isNotNull();
    }

    @Test
    public void uuids_any() throws Exception {
        final UUID any = fakeDataService.uuids().any();
        Assertions.assertThat(any).isNotNull();
    }


}