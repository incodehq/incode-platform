/*
 *  Copyright 2014 Dan Haywood
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
package org.incode.module.communications.demo.module.dom.impl.invoices;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.repository.RepositoryService;

import org.incode.module.communications.demo.module.dom.impl.customers.DemoCustomer;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = DemoInvoice.class
)
public class DemoInvoiceRepository {

    @Programmatic
    public List<DemoInvoice> listAll() {
        return repositoryService.allInstances(DemoInvoice.class);
    }

    @Programmatic
    public List<DemoInvoice> findByCustomer(final DemoCustomer demoCustomer) {
        return Lists.newArrayList(
                listAll().stream()
                        .filter(x -> Objects.equals(x.getCustomer(), demoCustomer))
                        .collect(Collectors.toList()));
    }

    @Programmatic
    public DemoInvoice create(
            final String num,
            final DemoCustomer customer) {
        final DemoInvoice obj = repositoryService.instantiate(DemoInvoice.class);
        obj.setNum(num);
        obj.setCustomer(customer);
        repositoryService.persist(obj);
        return obj;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;

}
