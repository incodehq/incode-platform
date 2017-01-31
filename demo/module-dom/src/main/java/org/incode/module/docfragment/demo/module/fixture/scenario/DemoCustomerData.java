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

package org.incode.module.docfragment.demo.module.fixture.scenario;

import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.repository.RepositoryService;

import org.incode.module.docfragment.demo.module.dom.impl.customers.DemoCustomer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@AllArgsConstructor
@Getter
@Accessors(chain = true)
public enum DemoCustomerData {

    Mr_Joe_Bloggs("Mr", "Joe", "Bloggs"),
    Ms_Joanna_Smith("Ms", "Joanna", "Smith"),
    Mrs_Betty_Flintstone("Mrs", "Betty", "Flintstone"),
    ;

    private final String title;
    private final String firstName;
    private final String lastName;

    @Programmatic
    public DemoCustomer createWith(final RepositoryService repositoryService) {
        final DemoCustomer domainObject = DemoCustomer.builder()
                .title(title)
                .firstName(firstName)
                .lastName(lastName)
                .build();
        repositoryService.persist(domainObject);
        return domainObject;
    }

}
