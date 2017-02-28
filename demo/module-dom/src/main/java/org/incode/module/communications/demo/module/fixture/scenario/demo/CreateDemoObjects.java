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

package org.incode.module.communications.demo.module.fixture.scenario.demo;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.repository.RepositoryService;

import org.incode.module.communications.demo.module.dom.impl.DemoObject;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class CreateDemoObjects extends FixtureScript {

    /**
     * The number of objects to create, up to 10; optional, defaults to 3.
     */
    @Nullable
    @Getter @Setter
    private Integer number;

    /**
     * The objects created by this fixture (output).
     */
    @Getter
    private final List<DemoObject> demoObjects = Lists.newArrayList();

    @Override
    protected void execute(final ExecutionContext ec) {

        // defaults
        final int number = defaultParam("number", ec, 3);

        // validate
        final int max = DemoObjectData.values().length;
        if(number < 0 || number > max) {
            throw new IllegalArgumentException(String.format("number must be in range [0,%d]", max));
        }

        // execute
        for (int i = 0; i < number; i++) {
            final DemoObject demoObject = DemoObjectData.values()[i].createWith(repositoryService);
            ec.addResult(this, demoObject);
            demoObjects.add(demoObject);
        }
    }

    @javax.inject.Inject
    RepositoryService repositoryService;

}
