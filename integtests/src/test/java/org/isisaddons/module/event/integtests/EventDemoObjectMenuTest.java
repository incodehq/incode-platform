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

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.fixturescripts.FixtureScripts;

import org.isisaddons.module.event.fixture.dom.EventDemoObject;
import org.isisaddons.module.event.fixture.dom.EventDemoObjectMenu;
import org.isisaddons.module.event.fixture.scripts.scenarios.EventDemoObjectsFixture;


public class EventDemoObjectMenuTest extends EventModuleIntegTest {

    @Inject
    FixtureScripts fixtureScripts;

    @Inject
    private EventDemoObjectMenu eventDemoObjectMenu;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new EventDemoObjectsFixture(), null);
    }


    @Test
    public void listAll() throws Exception {

        final List<EventDemoObject> all = wrap(eventDemoObjectMenu).listAll();
        Assertions.assertThat(all.size()).isEqualTo(3);
        
        EventDemoObject eventDemoObject = wrap(all.get(0));
        Assertions.assertThat(eventDemoObject.getName()).isEqualTo("Foo");
    }
    
    @Test
    public void create() throws Exception {

        wrap(eventDemoObjectMenu).create("Faz");
        
        final List<EventDemoObject> all = wrap(eventDemoObjectMenu).listAll();
        Assertions.assertThat(all.size()).isEqualTo(4);
    }

}