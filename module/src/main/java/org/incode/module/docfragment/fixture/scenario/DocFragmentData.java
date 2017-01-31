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

package org.incode.module.docfragment.fixture.scenario;

import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.repository.RepositoryService;

import org.incode.module.docfragment.dom.impl.DocFragment;
import org.incode.module.docfragment.dom.menu.DocFragmentMenu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@AllArgsConstructor
@Getter
@Accessors(chain = true)
public enum DocFragmentData {

    Customer_hello_GLOBAL("docfragmentdemo.DemoCustomer", "hello", "/", "Hello, nice to meet you, ${title} ${lastName}"),
    Customer_hello_ITA("docfragmentdemo.DemoCustomer", "hello", "/ITA", "Ciao, piacere di conoscerti, ${title} ${lastName}"),
    Customer_hello_FRA("docfragmentdemo.DemoCustomer", "hello", "/FRA", "Bonjour, ${title} ${lastName}, agréable de vous rencontrer"),
    Customer_goodbye_GLOBAL("docfragmentdemo.DemoCustomer", "goodbye", "/", "So long, ${firstName}"),
    Invoice_due_GLOBAL("docfragmentdemo.DemoInvoice", "due", "/", "The invoice will be due on the ${dueBy}, payable in ${numDays} days"),
    Invoice_due_FRA("docfragmentdemo.DemoInvoice", "due", "/FRA", "La facture sera due sur le ${dueBy}, payable dans ${numDays} jours");

    private final String objectType;
    private final String name;
    private final String atPath;
    private final String templateText;

    @Programmatic
    public DocFragment createWith(final RepositoryService repositoryService) {
        final DocFragment domainObject = DocFragment.builder()
                .objectType(this.getObjectType())
                .name(this.getName())
                .atPath(this.getAtPath())
                .templateText(this.getTemplateText())
                .build();
        repositoryService.persist(domainObject);
        return domainObject;
    }

    @Programmatic
    public DocFragment createWith(final DocFragmentMenu menu) {
        return menu.create(getObjectType(), getName(), getAtPath(), getTemplateText());
    }

    @Programmatic
    public DocFragment findWith(final RepositoryService repositoryService) {
        return repositoryService.uniqueMatch(DocFragment.class, x -> name().equals(x.getName()));
    }

}
