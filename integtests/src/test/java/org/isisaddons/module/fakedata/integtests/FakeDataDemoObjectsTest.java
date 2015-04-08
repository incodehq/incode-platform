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
package org.isisaddons.module.fakedata.integtests;

import java.util.List;
import javax.inject.Inject;
import org.isisaddons.module.fakedata.fixture.dom.FakeDataDemoObject;
import org.isisaddons.module.fakedata.fixture.dom.FakeDataDemoObjects;
import org.isisaddons.module.fakedata.fixture.scripts.scenarios.FakeDataDemoObjectsScenario;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FakeDataDemoObjectsTest extends FakeDataModuleIntegTest {

    @Before
    public void setUpData() throws Exception {
        scenarioExecution().install(new FakeDataDemoObjectsScenario());
    }

    @Inject
    private FakeDataDemoObjects fakeDataDemoObjects;

    @Test
    public void listAll() throws Exception {

        final List<FakeDataDemoObject> all = wrap(fakeDataDemoObjects).listAll();
        assertThat(all.size(), is(3));
        
        FakeDataDemoObject fakeDataDemoObject = wrap(all.get(0));
        assertThat(fakeDataDemoObject.getName(), is("Foo"));
    }
    
    @Test
    public void create() throws Exception {

        wrap(fakeDataDemoObjects).create("Faz");
        
        final List<FakeDataDemoObject> all = wrap(fakeDataDemoObjects).listAll();
        assertThat(all.size(), is(4));
    }

}