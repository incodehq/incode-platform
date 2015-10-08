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
package org.incode.module.commchannel.integtests;

import java.util.List;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.fixturescripts.FixtureScripts;

import org.incode.module.commchannel.fixture.dom.CommChannelDemoObject;
import org.incode.module.commchannel.fixture.dom.CommChannelDemoObjectMenu;
import org.incode.module.commchannel.fixture.scripts.scenarios.CommChannelDemoObjectsFixture;

public class CommChannelDemoObjectMenuTest extends CommChannelModuleIntegTest {

    @Inject
    FixtureScripts fixtureScripts;

    @Inject
    private CommChannelDemoObjectMenu commChannelDemoObjectMenu;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new CommChannelDemoObjectsFixture(), null);
    }


    @Test
    public void listAll() throws Exception {

        final List<CommChannelDemoObject> all = wrap(commChannelDemoObjectMenu).listAll();
        Assertions.assertThat(all.size()).isEqualTo(3);
        
        CommChannelDemoObject commChannelDemoObject = wrap(all.get(0));
        Assertions.assertThat(commChannelDemoObject.getName()).isEqualTo("Foo");
    }
    
    @Test
    public void create() throws Exception {

        wrap(commChannelDemoObjectMenu).create("Faz");
        
        final List<CommChannelDemoObject> all = wrap(commChannelDemoObjectMenu).listAll();
        Assertions.assertThat(all.size()).isEqualTo(4);
    }

}