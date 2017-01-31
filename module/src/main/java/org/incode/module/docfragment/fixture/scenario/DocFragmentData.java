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

    Customer_hello_GLOBAL("customers.Customer", "hello", "/", "Hello, nice to meet you, {this.title} {this.lastName}"),
    Customer_hello_ITA("customers.Customer", "hello", "/ITA", "Ciao, piacere di conoscerti, {this.title} {this.lastName}"),
    Customer_hello_FRA("customers.Customer", "hello", "/FRA", "Bonjour, {this.title} {this.lastName}, agréable de vous rencontrer"),
    Customer_goodbye_GLOBAL("customers.Customer", "goodbye", "/", "So long, {this.firstName}"),
    Invoice_due_GLOBAL("invoices.Invoice", "due", "/", "The invoice will be due on the {this.dueDate}, payable in {this.numDaysToPay} days"),
    Invoice_due_FRA("invoices.Invoice", "due", "/FRA", "La facture sera due sur le {this.dueDate}, payable dans {this.numDaysToPay} jours");

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
