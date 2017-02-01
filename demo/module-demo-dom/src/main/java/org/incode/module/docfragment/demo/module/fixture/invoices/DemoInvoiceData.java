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

package org.incode.module.docfragment.demo.module.fixture.invoices;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.repository.RepositoryService;

import org.incode.module.docfragment.demo.module.dom.impl.invoices.DemoInvoice;
import org.incode.module.fixturesupport.dom.data.DemoData;
import org.incode.module.fixturesupport.dom.data.DemoDataPersistAbstract;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DemoInvoiceData implements DemoData<DemoInvoiceData, DemoInvoice> {

    Invoice1(1, new LocalDate(2017,1,31), 30, "/"),
    Invoice2(2, new LocalDate(2017,1,20), 60, "/ITA"),
    Invoice3(3, new LocalDate(2017,1,25), 90, "/FRA"),
    ;

    private final int num;
    private final LocalDate dueBy;
    private final int numDay;
    private final String atPath;

    @Programmatic
    public DemoInvoice persistWith(final RepositoryService repositoryService) {
        return Util.persist(this, repositoryService);
    }

    @Programmatic
    public DemoInvoice findWith(final RepositoryService repositoryService) {
        return Util.uniqueMatch(this, repositoryService);
    }

    @Programmatic
    public DemoInvoice asDomainObject() {
        return DemoInvoice.builder()
                    .num(num)
                    .dueBy(dueBy)
                    .numDays(numDay)
                    .atPath(atPath)
                    .build();
    }

    public static class PersistScript extends DemoDataPersistAbstract<PersistScript, DemoInvoiceData, DemoInvoice> {
        public PersistScript() {
            super(DemoInvoiceData.class);
        }
    }


}
