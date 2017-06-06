/*
 *  Copyright 2016 Dan Haywood
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
package org.incode.module.classification.integtests.demo;

import org.assertj.core.api.Assertions;
import org.incode.module.classification.fixture.dom.demo.first.DemoObject;
import org.incode.module.classification.fixture.dom.demo.first.DemoObjectMenu;
import org.incode.module.classification.fixture.scripts.scenarios.ClassifiedDemoObjectsFixture;
import org.incode.module.classification.integtests.ClassificationModuleIntegTest;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;
import java.util.List;

public class DemoObjectMenuTest extends ClassificationModuleIntegTest {

    ClassifiedDemoObjectsFixture fs;

    @Before
    public void setUpData() throws Exception {
        fs = new ClassifiedDemoObjectsFixture();
        fixtureScripts.runFixtureScript(fs, null);
    }

    @Test
    public void listAll() throws Exception {

        // given
        int numDemoObjects = fs.getDemoObjects().size();

        // when
        final List<DemoObject> all = wrap(demoObjectMenu).listAll();

        // then
        Assertions.assertThat(all.size()).isEqualTo(numDemoObjects);
    }
    
    @Test
    public void create() throws Exception {

        // given
        final List<DemoObject> before = wrap(demoObjectMenu).listAll();
        int numBefore = before.size();

        // when
        wrap(demoObjectMenu).create("Faz", "/");

        // then
        final List<DemoObject> after = wrap(demoObjectMenu).listAll();
        Assertions.assertThat(after.size()).isEqualTo(numBefore+1);
    }

    @Inject
    DemoObjectMenu demoObjectMenu;


}