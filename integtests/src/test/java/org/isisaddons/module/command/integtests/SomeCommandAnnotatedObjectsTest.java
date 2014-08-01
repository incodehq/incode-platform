/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
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
package org.isisaddons.module.command.integtests;

import java.util.List;
import javax.inject.Inject;
import org.isisaddons.module.command.fixture.dom.SomeCommandAnnotatedObject;
import org.isisaddons.module.command.fixture.dom.SomeCommandAnnotatedObjects;
import org.isisaddons.module.command.fixture.scripts.SomeCommandAnnotatedObjectsFixture;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SomeCommandAnnotatedObjectsTest extends CommandModuleIntegTest {

    @Before
    public void setUpData() throws Exception {
        scenarioExecution().install(new SomeCommandAnnotatedObjectsFixture());
    }

    @Inject
    private SomeCommandAnnotatedObjects someCommandAnnotatedObjects;

    @Test
    public void listAll() throws Exception {

        final List<SomeCommandAnnotatedObject> all = wrap(someCommandAnnotatedObjects).listAll();
        assertThat(all.size(), is(3));
        
        SomeCommandAnnotatedObject someCommandAnnotatedObject = wrap(all.get(0));
        assertThat(someCommandAnnotatedObject.getName(), is("Foo"));
    }
    
    @Test
    public void create() throws Exception {

        wrap(someCommandAnnotatedObjects).create("Faz");
        
        final List<SomeCommandAnnotatedObject> all = wrap(someCommandAnnotatedObjects).listAll();
        assertThat(all.size(), is(4));
    }

}