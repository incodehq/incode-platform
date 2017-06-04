/*
 *  Copyright 2015 Dan Haywood
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
package org.isisaddons.module.publishmq.integtests;

import java.util.List;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.fixturescripts.FixtureScripts;

import org.isisaddons.module.publishmq.fixture.dom.PublishMqDemoObject;
import org.isisaddons.module.publishmq.fixture.dom.PublishMqDemoObjects;
import org.isisaddons.module.publishmq.fixture.scripts.scenarios.PublishMqDemoObjectsFixture;


public class PublishMqDemoObjectsTest extends PublishMqModuleIntegTest {

    @Inject
    FixtureScripts fixtureScripts;

    @Inject
    private PublishMqDemoObjects publishmqDemoObjects;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new PublishMqDemoObjectsFixture(), null);
    }


    @Test
    public void listAll() throws Exception {

        final List<PublishMqDemoObject> all = wrap(publishmqDemoObjects).listAll();
        Assertions.assertThat(all.size()).isEqualTo(3);
        
        PublishMqDemoObject publishmqDemoObject = wrap(all.get(0));
        Assertions.assertThat(publishmqDemoObject.getName()).isEqualTo("Foo");
    }
    
    @Test
    public void create() throws Exception {

        wrap(publishmqDemoObjects).create("Faz");
        
        final List<PublishMqDemoObject> all = wrap(publishmqDemoObjects).listAll();
        Assertions.assertThat(all.size()).isEqualTo(4);
    }

}