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

package org.isisaddons.module.tags.example.fixture;

import org.isisaddons.module.tags.example.dom.ExampleTaggableEntities;
import org.isisaddons.module.tags.example.dom.ExampleTaggableEntity;
import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

public class ExampleTaggableEntitiesAppSetUpFixture extends DiscoverableFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {

        // prereqs
        execute(new ExampleTaggableEntitiesAppTearDownFixture(), executionContext);

        // create
        create("Foo", "Coca Cola", "Drink");
        create("Bar", "Pepsi", "Drink");
        create("Baz", "McDonalds", "Fast food");
        create("Bop", "Levi's", "Clothing");
    }

    // //////////////////////////////////////

    private ExampleTaggableEntity create(String name, final String brand, String sector) {
        final ExampleTaggableEntity entity = exampleTaggableEntities.create(name, brand, sector);
        return entity;
    }

    // //////////////////////////////////////

    @javax.inject.Inject
    private ExampleTaggableEntities exampleTaggableEntities;

}
