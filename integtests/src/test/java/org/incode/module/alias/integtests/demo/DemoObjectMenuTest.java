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
package org.incode.module.alias.integtests.demo;

import java.util.List;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import org.incode.module.alias.fixture.dom.demo.DemoObject;
import org.incode.module.alias.fixture.dom.demo.DemoObjectMenu;
import org.incode.module.alias.fixture.scripts.scenarios.AliasDemoObjectsFixture;
import org.incode.module.alias.integtests.AliasModuleIntegTest;

public class DemoObjectMenuTest extends AliasModuleIntegTest {

    @Inject
    DemoObjectMenu demoObjectMenu;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new AliasDemoObjectsFixture(), null);
    }

    @Test
    public void listAll() throws Exception {

        final List<DemoObject> all = wrap(demoObjectMenu).listAll();
        Assertions.assertThat(all.size()).isEqualTo(3);
        
        DemoObject demoObject = wrap(all.get(0));
        Assertions.assertThat(demoObject.getName()).isEqualTo("Foo");
    }
    
    @Test
    public void create() throws Exception {

        wrap(demoObjectMenu).create("Faz");
        
        final List<DemoObject> all = wrap(demoObjectMenu).listAll();
        Assertions.assertThat(all.size()).isEqualTo(4);
    }

}