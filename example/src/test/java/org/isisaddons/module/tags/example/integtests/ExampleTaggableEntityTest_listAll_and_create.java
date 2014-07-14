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
package org.isisaddons.module.tags.example.integtests;

import java.util.List;
import javax.inject.Inject;
import org.isisaddons.module.tags.example.dom.ExampleTaggableEntities;
import org.isisaddons.module.tags.example.dom.ExampleTaggableEntity;
import org.isisaddons.module.tags.example.fixture.ExampleTaggableEntitiesAppSetUpFixture;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ExampleTaggableEntityTest_listAll_and_create extends ExampleTaggableEntitiesAppIntegTest {

    @Before
    public void setUpData() throws Exception {
        scenarioExecution().install(new ExampleTaggableEntitiesAppSetUpFixture());
    }

    @Inject
    private ExampleTaggableEntities exampleTaggableEntities;

    @Test
    public void listAll() throws Exception {

        final List<ExampleTaggableEntity> all = wrap(exampleTaggableEntities).listAll();
        assertThat(all.size(), is(3));
        
        ExampleTaggableEntity exampleTaggableEntity = wrap(all.get(0));
        assertThat(exampleTaggableEntity.getBrand(), is("Foo"));
    }
    
    @Test
    public void create() throws Exception {

        wrap(exampleTaggableEntities).create("Faz", "Pepsi", "Drink");
        
        final List<ExampleTaggableEntity> all = wrap(exampleTaggableEntities).listAll();
        assertThat(all.size(), is(4));
    }

}