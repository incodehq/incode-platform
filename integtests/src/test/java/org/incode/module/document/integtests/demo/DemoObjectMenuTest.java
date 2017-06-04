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
package org.incode.module.document.integtests.demo;

import java.util.List;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import org.incode.module.document.fixture.dom.demo.DemoObject;
import org.incode.module.document.fixture.dom.demo.DemoObjectMenu;
import org.incode.module.document.fixture.scripts.data.DemoObjectsFixture;
import org.incode.module.document.fixture.scripts.teardown.DocumentDemoAppTearDownFixture;
import org.incode.module.document.fixture.seed.DocumentTypeAndTemplatesApplicableForDemoObjectFixture;
import org.incode.module.document.integtests.DocumentModuleIntegTest;

public class DemoObjectMenuTest extends DocumentModuleIntegTest {

    @Inject
    DemoObjectMenu demoObjectMenu;

    public static final int NUMBER = 5;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new DocumentDemoAppTearDownFixture(), null);
        fixtureScripts.runFixtureScript(new DocumentTypeAndTemplatesApplicableForDemoObjectFixture(), null);
        fixtureScripts.runFixtureScript(new DemoObjectsFixture().setNumber(NUMBER), null);
    }

    @Test
    public void listAll() throws Exception {

        final List<DemoObject> demoObjects = wrap(demoObjectMenu).listAll();
        Assertions.assertThat(demoObjects.size()).isEqualTo(NUMBER);

        for (DemoObject demoObject : demoObjects) {
            Assertions.assertThat(wrap(demoObject).getName()).isNotNull();
            Assertions.assertThat(wrap(demoObject).getUrl()).isNotNull();

        }
    }
    


}