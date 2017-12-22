package org.incode.example.note.dom.impl.calendarname;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2;

import org.incode.example.note.dom.spi.CalendarNameRepository;

import static org.assertj.core.api.Assertions.assertThat;

public class CalendarNameServiceTest {

    @Rule
    public JUnitRuleMockery2 context = JUnitRuleMockery2.createFor(JUnitRuleMockery2.Mode.INTERFACES_AND_CLASSES);

    @Mock
    CalendarNameRepository mockCalendarNameRepository;

    CalendarNameService calendarNameService;

    @Before
    public void setUp() throws Exception {
        calendarNameService = new CalendarNameService();
        calendarNameService.calendarNameRepository = mockCalendarNameRepository;
    }

    public static class CalendarNamesForTest extends CalendarNameServiceTest {

        @Test
        public void when_repository_returns_values() throws Exception {

            // given
            final Object notable = new Object(){};
            final List<String> calendarNames = Lists.newArrayList("a", "b", "c");

            // expecting
            context.checking(new Expectations() {{
                oneOf(mockCalendarNameRepository).calendarNamesFor(notable);
                will(returnValue(calendarNames));
            }});

            // when
            final Collection<String> returnedCalendarNames = calendarNameService.calendarNamesFor(notable);

            // then
            assertThat(returnedCalendarNames).isEqualTo(calendarNames);
        }


        @Test
        public void when_repository_returns_null() throws Exception {

            // given
            final Object notable = new Object(){};

            // expecting
            context.checking(new Expectations() {{
                oneOf(mockCalendarNameRepository).calendarNamesFor(notable);
                will(returnValue(null));
            }});

            // when
            final Collection<String> returnedCalendarNames = calendarNameService.calendarNamesFor(notable);

            // then
            assertThat(returnedCalendarNames).hasSize(1);
            assertThat(returnedCalendarNames).containsExactly(CalendarNameService.DEFAULT_CALENDAR_NAME);
        }

    }

}