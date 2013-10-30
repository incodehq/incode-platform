/*
 *  Copyright 2013 Dan Haywood
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package com.danhaywood.isis.wicket.fullcalendar2.applib;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Locale;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CalendarEventTest {

    private Locale currentLocale;

    @Before
    public void setUp() throws Exception {
        currentLocale = Locale.getDefault();
    }
    
    @After
    public void tearDown() throws Exception {
        Locale.setDefault(currentLocale);
    }

    @Test
    public void test3ArgConstructor() {
        CalendarEvent calendarEvent = new CalendarEvent(newDateTime(2013,4,1), "foobar", "CAR-1234 foobar");
        assertThat(calendarEvent.getDateTime(), is(newDateTime(2013,4,1)));
        assertThat(calendarEvent.getCalendarName(), is("foobar"));
        assertThat(calendarEvent.getTitle(), is("CAR-1234 foobar"));
        assertThat(calendarEvent.getNotes(), is(nullValue()));
    }
    
    @Test
    public void test4ArgConstructor() {
        CalendarEvent calendarEvent = new CalendarEvent(newDateTime(2013,4,1), "foobar", "CAR-1234 foobar", "some notes");
        assertThat(calendarEvent.getDateTime(), is(newDateTime(2013,4,1)));
        assertThat(calendarEvent.getCalendarName(), is("foobar"));
        assertThat(calendarEvent.getTitle(), is("CAR-1234 foobar"));
        assertThat(calendarEvent.getNotes(), is("some notes"));
    }

    @Test
    public void testWithDateTime() {
        CalendarEvent calendarEvent = new CalendarEvent(newDateTime(2013,4,1), "foobar", "CAR-1234 foobar", "some notes");
        CalendarEvent calendarEvent2 = calendarEvent.withDateTime(newDateTime(2014,5,2));
        Assert.assertFalse(calendarEvent2 == calendarEvent);
        assertThat(calendarEvent.getDateTime(), is(newDateTime(2013,4,1)));
        assertThat(calendarEvent2.getDateTime(), is(newDateTime(2014,5,2)));
        assertThat(calendarEvent2.getCalendarName(), is("foobar"));
        assertThat(calendarEvent2.getTitle(), is("CAR-1234 foobar"));
        assertThat(calendarEvent2.getNotes(), is("some notes"));
    }

    @Test
    public void testWithCalendarName() {
        CalendarEvent calendarEvent = new CalendarEvent(newDateTime(2013,4,1), "foobar", "CAR-1234 foobar", "some notes");
        CalendarEvent calendarEvent2 = calendarEvent.withCalendarName("barfoo");
        Assert.assertFalse(calendarEvent2 == calendarEvent);
        assertThat(calendarEvent.getCalendarName(), is("foobar"));
        assertThat(calendarEvent2.getDateTime(), is(newDateTime(2013,4,1)));
        assertThat(calendarEvent2.getCalendarName(), is("barfoo"));
        assertThat(calendarEvent2.getTitle(), is("CAR-1234 foobar"));
        assertThat(calendarEvent2.getNotes(), is("some notes"));
    }

    @Test
    public void testWithTitle() {
        CalendarEvent calendarEvent = new CalendarEvent(newDateTime(2013,4,1), "foobar", "CAR-1234 foobar", "some notes");
        CalendarEvent calendarEvent2 = calendarEvent.withTitle("CAR-1234 barfoo");
        Assert.assertFalse(calendarEvent2 == calendarEvent);
        assertThat(calendarEvent.getCalendarName(), is("foobar"));
        assertThat(calendarEvent2.getDateTime(), is(newDateTime(2013,4,1)));
        assertThat(calendarEvent2.getCalendarName(), is("foobar"));
        assertThat(calendarEvent2.getTitle(), is("CAR-1234 barfoo"));
        assertThat(calendarEvent2.getNotes(), is("some notes"));
    }
    
    @Test
    public void testWithNotes() {
        CalendarEvent calendarEvent = new CalendarEvent(newDateTime(2013,4,1), "foobar", "CAR-1234 foobar", "some notes");
        CalendarEvent calendarEvent2 = calendarEvent.withNotes("other notes");
        Assert.assertFalse(calendarEvent2 == calendarEvent);
        assertThat(calendarEvent.getNotes(), is("some notes"));
        assertThat(calendarEvent2.getDateTime(), is(newDateTime(2013,4,1)));
        assertThat(calendarEvent2.getCalendarName(), is("foobar"));
        assertThat(calendarEvent2.getTitle(), is("CAR-1234 foobar"));
        assertThat(calendarEvent2.getNotes(), is("other notes"));
    }
    
    @Test
    public void testEqualsAndHashCode() {
        CalendarEvent calendarEvent = new CalendarEvent(newDateTime(2013,4,1), "foobar", "some notes");
        CalendarEvent calendarEvent2 = new CalendarEvent(newDateTime(2013,4,1), "foobar", "other notes");
        CalendarEvent calendarEvent3 = new CalendarEvent(newDateTime(2013,4,1), "foobaZ", "other notes");
        CalendarEvent calendarEvent4 = new CalendarEvent(newDateTime(2013,4,2), "foobar", "other notes");
        
        Assert.assertFalse(calendarEvent2 == calendarEvent);
        assertThat(calendarEvent2, is(calendarEvent));
        assertThat(calendarEvent2, is(not(calendarEvent3)));
        assertThat(calendarEvent2, is(not(calendarEvent4)));
        
        assertThat(calendarEvent2.hashCode(), is(calendarEvent.hashCode()));
    }
    

    @Test
    public void testToString() {
        CalendarEvent calendarEvent = new CalendarEvent(newDateTime(2013,4,1), "foobar", "some notes");
        assertThat(calendarEvent.toString(), matches("CalendarEvent\\{dateTime=2013-04-01.*, calendarName=foobar}"));
    }
    
    private static DateTime newDateTime(int y, int m, int d) {
        return new DateTime(y,m,d,0,0);
    }


    static class RegexMatcher extends TypeSafeMatcher<String> {
        private final String regex;

        public RegexMatcher(String regex){
            this.regex = regex;
        }

        @Override
        protected boolean matchesSafely(String item) {
            return item.matches(regex);
        }

        public void describeTo(Description description){
            description.appendText("matches regex '").appendText(this.regex).appendText("'");
        }
    }
    private static RegexMatcher matches(String regex){
        return new RegexMatcher(regex);
    }
}
