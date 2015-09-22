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
package org.isisaddons.module.commchannel.integtests;

import java.util.List;

import javax.inject.Inject;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.fixturescripts.FixtureScripts;

import org.isisaddons.module.commchannel.dom.EventContributions;
import org.isisaddons.module.commchannel.fixture.dom.CalendarName;
import org.isisaddons.module.commchannel.fixture.dom.CommChannelDemoObject;
import org.isisaddons.module.commchannel.fixture.dom.CommChannelDemoObjectMenu;
import org.isisaddons.module.commchannel.fixture.scripts.teardown.CommChannelDemoObjectsTearDownFixture;

import static org.assertj.core.api.Assertions.assertThat;

public class CommChannelDemoObjectTest extends CommChannelModuleIntegTest {

    @Inject
    FixtureScripts fixtureScripts;

    @Inject
    CommChannelDemoObjectMenu commChannelDemoObjectMenu;

    @Inject
    EventContributions eventContributions;

    CommChannelDemoObject commChannelDemoObject;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new CommChannelDemoObjectsTearDownFixture(), null);

        commChannelDemoObject = wrap(commChannelDemoObjectMenu).create("Foo");
    }


    public static class AddCommChannel extends CommChannelDemoObjectTest {

        @Test
        public void canAdd() throws Exception {

            // given
            assertThat(eventContributions.events(commChannelDemoObject)).isEmpty();

            // when
            final LocalDate now = LocalDate.now();
            commChannelDemoObject.addEvent(CalendarName.BLUE, now);

            // then
            assertThat(eventContributions.events(commChannelDemoObject)).hasSize(1);

        }

        @Test
        public void canAddTwo() throws Exception {

            // given
            assertThat(eventContributions.events(commChannelDemoObject)).isEmpty();
            final LocalDate now = LocalDate.now();
            commChannelDemoObject.addEvent(CalendarName.BLUE, now);

            // when
            commChannelDemoObject.addEvent(CalendarName.GREEN, now);

            // then
            assertThat(eventContributions.events(commChannelDemoObject)).hasSize(2);

        }

        /**
         * This is really testing the demo object, rather than the event module...
         */
        @Test
        public void cannotAddToCalendarMoreThanOnce() throws Exception {

            // given
            assertThat(eventContributions.events(commChannelDemoObject)).isEmpty();
            final LocalDate now = LocalDate.now();
            commChannelDemoObject.addEvent(CalendarName.BLUE, now);
            commChannelDemoObject.addEvent(CalendarName.GREEN, now);

            // when
            final List<CalendarName> calendarNames = commChannelDemoObject.choices0AddEvent();

            // then
            assertThat(calendarNames).hasSize(1);
            assertThat(calendarNames.get(0)).isEqualTo(CalendarName.RED);
        }

    }

}