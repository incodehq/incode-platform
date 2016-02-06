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

import org.incode.module.alias.fixture.dom.aliasdemoobject.AliasDemoObject;
import org.incode.module.alias.fixture.dom.aliasdemoobject.AliasDemoObjectMenu;
import org.incode.module.alias.fixture.scripts.scenarios.AliasDemoObjectsFixture;
import org.incode.module.alias.integtests.AliasModuleIntegTest;

public class AliasDemoObjectMenuTest extends AliasModuleIntegTest {

    @Inject
    AliasDemoObjectMenu aliasDemoObjectMenu;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new AliasDemoObjectsFixture(), null);
    }

    @Test
    public void listAll() throws Exception {

        final List<AliasDemoObject> all = wrap(aliasDemoObjectMenu).listAll();
        Assertions.assertThat(all.size()).isEqualTo(3);
        
        AliasDemoObject aliasDemoObject = wrap(all.get(0));
        Assertions.assertThat(aliasDemoObject.getName()).isEqualTo("Foo");
    }
    
    @Test
    public void create() throws Exception {

        wrap(aliasDemoObjectMenu).create("Faz");
        
        final List<AliasDemoObject> all = wrap(aliasDemoObjectMenu).listAll();
        Assertions.assertThat(all.size()).isEqualTo(4);
    }

}