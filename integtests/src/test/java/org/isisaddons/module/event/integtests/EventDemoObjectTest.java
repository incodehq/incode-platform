/*
 *  Copyright 2014~2015 Dan Haywood
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
package org.isisaddons.module.event.integtests;

import java.util.List;

import javax.inject.Inject;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.fixturescripts.FixtureScripts;

import org.isisaddons.module.event.dom.EventContributions;
import org.isisaddons.module.event.fixture.dom.CalendarName;
import org.isisaddons.module.event.fixture.dom.EventDemoObject;
import org.isisaddons.module.event.fixture.dom.EventDemoObjectMenu;
import org.isisaddons.module.event.fixture.scripts.teardown.EventDemoObjectsTearDownFixture;

import static org.assertj.core.api.Assertions.assertThat;

public class EventDemoObjectTest extends EventModuleIntegTest {

    @Inject
    FixtureScripts fixtureScripts;

    @Inject
    EventDemoObjectMenu eventDemoObjectMenu;

    @Inject
    EventContributions eventContributions;

    EventDemoObject eventDemoObject;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new EventDemoObjectsTearDownFixture(), null);

        eventDemoObject = wrap(eventDemoObjectMenu).create("Foo");
    }


    public static class AddEvent extends EventDemoObjectTest {

        @Test
        public void canAdd() throws Exception {

            // given
            assertThat(eventContributions.events(eventDemoObject)).isEmpty();

            // when
            final LocalDate now = LocalDate.now();
            eventDemoObject.addEvent(CalendarName.BLUE, now);

            // then
            assertThat(eventContributions.events(eventDemoObject)).hasSize(1);

        }

        @Test
        public void canAddTwo() throws Exception {

            // given
            assertThat(eventContributions.events(eventDemoObject)).isEmpty();
            final LocalDate now = LocalDate.now();
            eventDemoObject.addEvent(CalendarName.BLUE, now);

            // when
            eventDemoObject.addEvent(CalendarName.GREEN, now);

            // then
            assertThat(eventContributions.events(eventDemoObject)).hasSize(2);

        }

        /**
         * This is really testing the demo object, rather than the event module...
         */
        @Test
        public void cannotAddToCalendarMoreThanOnce() throws Exception {

            // given
            assertThat(eventContributions.events(eventDemoObject)).isEmpty();
            final LocalDate now = LocalDate.now();
            eventDemoObject.addEvent(CalendarName.BLUE, now);
            eventDemoObject.addEvent(CalendarName.GREEN, now);

            // when
            final List<CalendarName> calendarNames = eventDemoObject.choices0AddEvent();

            // then
            assertThat(calendarNames).hasSize(1);
            assertThat(calendarNames.get(0)).isEqualTo(CalendarName.RED);
        }

    }

}