package org.isisaddons.module.freemarker.dom.service;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertThat;

public class FreeMarkerService_Joda_Test {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Rule
    public JUnitRuleMockery2 context = JUnitRuleMockery2.createFor(JUnitRuleMockery2.Mode.INTERFACES_AND_CLASSES);

    @JUnitRuleMockery2.Ignoring

    Map<String, Object> properties;

    FreeMarkerService service;

    @Before
    public void setUp() throws Exception {
        service = new FreeMarkerService();
        properties = ImmutableMap.of(
                "jodaDateTime", new org.joda.time.DateTime(2017,2,1,14,30),
                "jodaLocalDateTime", new org.joda.time.LocalDateTime(2017,2,1,14,30),
                "jodaLocalDate", new org.joda.time.LocalDate(2017,2,1),
                "jodaLocalTime", new org.joda.time.LocalTime(14,30,45)
        );
    }

    @Test
    public void joda_date_time() throws Exception {

        // given
        service.init(ImmutableMap.of(FreeMarkerService.JODA_SUPPORT_KEY, "true"));

        // when
        String merged = service.render(
                "WelcomeUserTemplate:/GBR:",
                "result: ${jodaDateTime?string[\"dd-MMM-yyyy HH:mm:ss\"]}", properties);

        // then
        assertThat(merged, is("result: 01-Feb-2017 14:30:00"));
    }

    @Test
    public void joda_support_disabled() throws Exception {

        // given
        service.init(ImmutableMap.of(FreeMarkerService.JODA_SUPPORT_KEY, "false"));

        // when
        String merged = service.render(
                "WelcomeUserTemplate:/GBR:",
                "result: ${jodaDateTime}", properties);

        // then
        // TODO: this doesn't play well with time zones, so cheating by using 'startsWith'
        assertThat(merged, startsWith("result: 2017-02-01T14:30:00"));
    }

    @Test
    public void joda_date_time_with_formats() throws Exception {

        // given
        service.init(ImmutableMap.of(FreeMarkerService.JODA_SUPPORT_KEY, "true"));


        // when
        String merged = service.render(
                "WelcomeUserTemplate:/GBR:",
                "result: ${jodaDateTime?string[\"dd-MMM-yyyy\"]}", properties);

        // then
        assertThat(merged, is("result: 01-Feb-2017"));

        // when
        merged = service.render(
                "WelcomeUserTemplate:/GBR2:",
                "result: ${jodaDateTime?string[\"dd-MMM-yyyy HH:mm:ss\"]}", properties);

        // then
        assertThat(merged, is("result: 01-Feb-2017 14:30:00"));

        // when
        merged = service.render(
                "WelcomeUserTemplate:/GBR3:",
                "result: ${jodaDateTime?string[\"HH:mm:ss\"]}", properties);

        // then
        assertThat(merged, is("result: 14:30:00"));
    }

    @Test
    public void joda_local_date_time() throws Exception {

        // given
        service.init(ImmutableMap.of(FreeMarkerService.JODA_SUPPORT_KEY, "true"));


        // when
        String merged = service.render(
                "WelcomeUserTemplate:/GBR:",
                "result: ${jodaLocalDateTime?string[\"dd-MMM-yyyy HH:mm:ss\"]}", properties);

        // then
        assertThat(merged, is("result: 01-Feb-2017 14:30:00"));
    }

    @Test
    public void joda_local_date() throws Exception {

        // given
        service.init(ImmutableMap.of(FreeMarkerService.JODA_SUPPORT_KEY, "true"));

        // when
        String merged = service.render(
                "WelcomeUserTemplate:/GBR:",
                "result: ${jodaLocalDate?string[\"dd-MMM-yyyy\"]}", properties);

        // then
        assertThat(merged, is("result: 01-Feb-2017"));
    }

    @Test
    public void joda_local_time() throws Exception {

        // given
        service.init(ImmutableMap.of(FreeMarkerService.JODA_SUPPORT_KEY, "true"));

        // when
        String merged = service.render(
                "WelcomeUserTemplate:/GBR:",
                "result: ${jodaLocalTime?string[\"HH:mm:ss\"]}", properties);

        // then
        assertThat(merged, is("result: 14:30:45"));
    }


}
